package com.example.job.ui.resume

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.transition.TransitionManager
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lexample.job.App
import com.example.job.BaseFragment
import com.example.job.ChatActivity
import com.example.job.R
import com.example.job.model.RequestData
import com.example.job.network.data.input.CV
import com.example.job.network.data.input.DuplicateResponse
import com.example.job.network.data.input.Experience
import com.example.job.network.data.input.JobSeeker
import com.example.job.ui.complaint.ComplaintActivity
import com.example.job.ui.context_menu.ContextMenuFragment
import com.example.job.ui.context_menu.ContextMenuItem
import com.example.job.ui.experience.ExperienceEditCallback
import com.example.job.util.Const
import com.example.job.util.Const.ACTION_RESUME_UPDATE
import com.example.job.util.Const.REQUEST_DIRECTORY
import com.example.job.util.FileAmazon3Util
import com.yandex.runtime.Runtime.getApplicationContext
import kotlinx.android.synthetic.main.resume_info_activity.*
import okhttp3.ResponseBody
import java.io.*


class ResumeInfoViewModel(var data: CV, var jobSeeker: JobSeeker, val fragment: BaseFragment) :
    ViewModel(), ExperienceEditCallback {
    val TAG = "ResumeInfoViewModel"

    val downloading = ObservableField<Boolean>(false)
    val showBtnViewVideo = ObservableField<Boolean>(false)
    val showBtnSendMessage = ObservableField<Boolean>(false)
    val showBtnOpenChat = ObservableField<Boolean>(false)
    val showBtnMenu = ObservableField<Boolean>(true)
    var roomId = ""

    override fun onAddExperience(data: Experience) {
    }

    override fun onEditExperience(data: Experience) {
    }

    override fun onDeleteExperience(data: Experience) {
    }

    val adapter: ResumeExpAdapter? = ResumeExpAdapter(false, fragment, this)

    val callbackGetPDF: RequestData<ResponseBody> = object : RequestData<ResponseBody> {

        override fun onSuccess(response: ResponseBody) {
            downloading.set(false)
            if (response != null) {
                filePdf = response.bytes()


                val targetFilename =
                    App.resourses!!.getString(R.string.format_string_filename) + " " + jobSeeker.name + " " + data.position + ".pdf"

                val intent = Intent()
                    .setType("file/pdf")
                    .setAction(Intent.ACTION_CREATE_DOCUMENT)
                    .putExtra(Intent.EXTRA_TITLE, targetFilename)

                fragment.startActivityForResult(
                    Intent.createChooser(
                        intent,
                        "Choose path to save..."
                    ), REQUEST_DIRECTORY
                )
            }
        }

        override fun onError(error: Throwable) {
            downloading.set(false)
            fragment.showWarning(error.message!!)
        }
    }

    val callbackDuplicateCvs: RequestData<DuplicateResponse> =
        object : RequestData<DuplicateResponse> {

            override fun onSuccess(response: DuplicateResponse) {
                fragment.onBackPressed()
            }

            override fun onError(error: Throwable) {
                fragment.showWarning(error.message!!)
            }
        }

    val callbackDeleteCvs: RequestData<Unit?> = object : RequestData<Unit?> {

        override fun onSuccess(response: Unit?) {
            fragment.onBackPressed()
        }

        override fun onError(error: Throwable) {
            fragment.onBackPressed()
        }
    }

    val callbackEditCvs: RequestData<CV> = object : RequestData<CV> {

        override fun onSuccess(response: CV) {
            fragment.onBackPressed()
        }

        override fun onError(error: Throwable) {
            fragment.onBackPressed()
        }
    }

    val callbackDialogIsExist: RequestData<String> = object : RequestData<String> {

        override fun onSuccess(response: String) {
            roomId = response
            showBtnOpenChat.set(true)
            showBtnSendMessage.set(false)
        }

        override fun onError(error: Throwable) {
            showBtnOpenChat.set(false)
        }
    }

    val callbackCreateDialog: RequestData<String> = object : RequestData<String> {

        override fun onSuccess(response: String) {
            roomId = response
            showBtnOpenChat.set(true)
            showBtnSendMessage.set(false)
            onClickOpenChat(data, jobSeeker)
        }

        override fun onError(error: Throwable) {
            if (error.message != null)
                fragment.showWarning(error.message!!)
            showBtnOpenChat.set(false)
        }
    }


    fun update(dataNew: CV, jobSeekerNew: JobSeeker) {
        data = dataNew
        jobSeeker = jobSeekerNew
    }

    var imageUrl = ObservableField<String>("")

    val callbackLoadImage = object : RequestData<String> {
        override fun onSuccess(data: String) {
            val file: File = File(data)
            imageUrl.set(file.absolutePath)
        }

        override fun onError(error: Throwable) {
            fragment.showWarning(error.message!!)
        }

    }

    fun init() {
        if (App.instance!!.isRoleEmployer()) {
            showBtnSendMessage.set(true)
            showBtnMenu.set(true)
        }
        App.instance!!.rocketChatClient.dialogIsExist(jobSeeker.phone, callbackDialogIsExist)

        FileAmazon3Util().initAndRead(
            jobSeeker.avatarId,
            fragment.context!!.getFilesDir().getPath().toString(),
            callbackLoadImage
        )

        adapter!!.setData(data.experience)
        adapter!!.notifyDataSetChanged()
    }

    fun onClickBack() {
        fragment.onBackPressed()
    }

    fun onClickRespond(data: CV) {
        fragment.onBackPressed()
    }

    fun onClickSendMessage(data: CV, profile: JobSeeker) {
        App.instance!!.rocketChatClient.createDialog(jobSeeker.phone, callbackCreateDialog)
    }

    fun onClickOpenChat(data: CV, profile: JobSeeker) {
        val intent = Intent(fragment.activity!!.baseContext, ChatActivity::class.java)
        intent.putExtra(Const.KEY_ROOM_ID, roomId)
        intent.putExtra(Const.JOBSEEK_DATA, profile)
        fragment.activity!!.startActivity(intent)
    }

    lateinit var menuItem: List<ContextMenuItem>

    fun showPopUpDelete() {
        val view = fragment.layoutInflater.inflate(R.layout.popup_delete_item, null)
        val popupWindow = PopupWindow(
            view,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }
        val buttonPopupSkip = view.findViewById<Button>(R.id.btn_popup_skip)
        val buttonPopupContinue = view.findViewById<Button>(R.id.btn_popup_continue)

        buttonPopupSkip.setOnClickListener {
            popupWindow.dismiss()
        }

        buttonPopupContinue.setOnClickListener {
            App.instance!!.requestModel.deleteCvsMine(data.id, callbackDeleteCvs)
            popupWindow.dismiss()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(fragment.activity!!.container)
        }
        popupWindow.showAtLocation(
            fragment.activity!!.container,
            Gravity.CENTER,
            0, 0
        )
    }

    fun onClickMenu() {
        val manager = fragment.activity!!.supportFragmentManager
        val transaction = manager.beginTransaction()
        if (App.instance!!.isRoleJobSeeker()) {
            menuItem = listOf(
                ContextMenuItem(
                    R.string.edit,
                    R.color.colorBlackText,
                    true,
                    View.OnClickListener { view ->
                        fragment.onBackPressed()
                        val intent = Intent(fragment.context, ResumeEditActivity::class.java)
                        intent.putExtra(Const.RESUME_DATA, data)
                        intent.putExtra(Const.JOBSEEK_DATA, jobSeeker)
                        intent.putExtra(Const.ACTION_RESUME, Const.ACTION_EDIT)
                        fragment.startActivityForResult(intent, ACTION_RESUME_UPDATE)
                    }),
                ContextMenuItem(
                    R.string.duplicate,
                    R.color.colorBlackText,
                    true,
                    View.OnClickListener { view ->
                        App.instance!!.requestModel.duplicateCvsMine(data.id, callbackDuplicateCvs)
                    }),
                ContextMenuItem(
                    R.string.download,
                    R.color.colorBlackText,
                    true,
                    View.OnClickListener { view ->
                        App.instance!!.requestModel.getCvsPDF(data.id, callbackGetPDF)
                        downloading.set(true)
                        fragment.onBackPressed()
                    }),
                ContextMenuItem(
                    R.string.delete,
                    R.color.colorOrange,
                    false,
                    View.OnClickListener { view ->
                        showPopUpDelete()
                        fragment.onBackPressed()
                    })
            )
        } else {
            menuItem = listOf(
                ContextMenuItem(
                    R.string.download,
                    R.color.colorBlackText,
                    false,
                    View.OnClickListener { view ->
                        App.instance!!.requestModel.getCvsPDF(data.id, callbackGetPDF)
                        downloading.set(true)
                        fragment.onBackPressed()
                    }),
                ContextMenuItem(
                    R.string.report,
                    R.color.colorBlackText,
                    false,
                    View.OnClickListener { view ->
                        val intent = Intent(fragment.context, ComplaintActivity::class.java)
                        intent.putExtra(Const.ID_FOR_COMPLAINT, data.id)
                        fragment.startActivity(intent)
                        fragment.onBackPressed()
                    }),
                ContextMenuItem(R.string.cancel, R.color.colorOrange, false, View.OnClickListener { view ->
                    fragment.onBackPressed()
                })
            )
        }
        val fragment = ContextMenuFragment.newInstance(menuItem)
        transaction.addToBackStack("MENU")
        transaction.add(R.id.container, fragment, "MENU")
        transaction.commit()
    }

    var filePdf: ByteArray? = null

    fun saveFile(path: Uri) {
        if (filePdf != null) {
            val output: OutputStream? =
                fragment.activity!!.getContentResolver().openOutputStream(path)

            output!!.write(filePdf!!)
            output!!.flush()
            output!!.close()
        }
    }

    companion object {

        @JvmStatic
        @BindingAdapter("setImage")
        fun loadImage(view: ImageView, imageUrl: String) {
            Glide.with(view.getContext())
                .load(imageUrl).apply(RequestOptions().centerCrop())
                .into(view)
        }

        @JvmStatic
        @BindingAdapter("setImage")
        fun loadImage(view: ImageView, imageUrl: Uri) {
            Glide.with(view.getContext())
                .load(imageUrl).apply(RequestOptions().centerCrop())
                .into(view)
        }
    }
}