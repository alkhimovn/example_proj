package com.example.job.ui.resume

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.job.BaseFragment
import com.example.job.R
import com.example.job.databinding.ResumeFragmentBinding
import com.example.job.databinding.ResumeInfoFragmentBinding
import com.example.job.model.data.ResumeData
import com.example.job.network.data.input.CV
import com.example.job.network.data.input.JobSeeker
import com.example.job.util.Const.*


class ResumeInfoFragment : BaseFragment() {

    lateinit var data: CV
    lateinit var jobSeeker: JobSeeker

    private var listenerOnFragmentInteraction: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listenerOnFragmentInteraction = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerOnFragmentInteraction = null
    }

    companion object {
        @JvmStatic
        fun newInstance(data: CV, jobSeeker: JobSeeker) =
            ResumeInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(RESUME_DATA, data)
                    putSerializable(JOBSEEK_DATA, jobSeeker)
                }
            }
    }

    private lateinit var viewModel : ResumeInfoViewModel
    private lateinit var binding: ResumeInfoFragmentBinding
    private var mInflater: LayoutInflater? = null
    private var mContainer: ViewGroup? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        arguments?.let {
            data = it.getSerializable(RESUME_DATA) as CV
            jobSeeker = it.getSerializable(JOBSEEK_DATA) as JobSeeker
        }

        viewModel = ResumeInfoViewModel(data, jobSeeker, this)

        binding = DataBindingUtil.inflate(inflater, R.layout.resume_info_fragment, container, false)
        binding.viewModel = viewModel
        mInflater = inflater
        mContainer = container

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.executePendingBindings()
        viewModel.init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            ACTION_RESUME_UPDATE -> {
                if (data != null)
                    if (data!!.hasExtra(RESUME_DATA)) {
                        var cv = data!!.getSerializableExtra(RESUME_DATA) as CV
                        var jobSeeker = data!!.getSerializableExtra(JOBSEEK_DATA) as JobSeeker
                        if (cv.id != -1) {
                            viewModel = ResumeInfoViewModel(cv, jobSeeker, this)
                            binding.viewModel = viewModel
                            viewModel.update(cv, jobSeeker)
                            binding.executePendingBindings()
                            viewModel.init()
                        } else
                            activity!!.finish()
                    }
            }
            REQUEST_DIRECTORY -> {
                if (resultCode == RESULT_OK)
                    if (data != null) {
                        var path = data!!.data
                        viewModel.saveFile(path)
                    }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
