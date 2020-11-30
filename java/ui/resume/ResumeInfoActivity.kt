package com.example.job.ui.resume

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.job.BaseActivity
import com.example.job.BaseFragment
import com.example.job.R
import com.example.job.model.data.ResumeData
import com.example.job.network.data.input.CV
import com.example.job.network.data.input.JobSeeker
import com.example.job.util.Const
import com.example.job.util.Const.RESUME_DATA

class ResumeInfoActivity : BaseActivity(), BaseFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resume_info_activity)
        val data: CV = intent.getSerializableExtra(RESUME_DATA) as CV
        val jobSeeker = intent.getSerializableExtra(Const.JOBSEEK_DATA) as JobSeeker
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ResumeInfoFragment.newInstance(data, jobSeeker))
                .commitNow()
        }
    }

}
