package com.hieupham.absolutecleanarchitecturekt.feature.transactiondetail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import com.hieupham.absolutecleanarchitecturekt.R
import com.hieupham.absolutecleanarchitecturekt.feature.BaseSupportFragment
import com.hieupham.absolutecleanarchitecturekt.feature.BaseViewModel
import com.hieupham.absolutecleanarchitecturekt.model.CompositeTransactionModelView
import javax.inject.Inject

class TransactionDetailFragment : BaseSupportFragment() {

    companion object {
        private const val TRANSACTION = "TRANSACTION"

        fun newInstance(transaction: CompositeTransactionModelView): TransactionDetailFragment {
            val bundle = Bundle()
            bundle.putParcelable(TRANSACTION, transaction)
            val fragment = TransactionDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    internal lateinit var viewModel: ViewModel

    @BindView(R.id.text_title)
    internal lateinit var textViewTitle: TextView

    @BindView(R.id.text_asset_name)
    internal lateinit var textViewAssetName: TextView

    @BindView(R.id.text_block_name)
    internal lateinit var textViewBlockName: TextView

    @BindView(R.id.text_description)
    internal lateinit var textViewDescription: TextView

    @BindView(R.id.text_bitmark_id)
    internal lateinit var textViewBitmarkId: TextView

    @BindView(R.id.text_meta_data)
    internal lateinit var textViewMetaData: TextView

    @BindView(R.id.progressbar)
    internal lateinit var progressBar: ProgressBar

    @BindView(R.id.toolbar)
    internal lateinit var toolbar: Toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val transaction = arguments?.getParcelable(
                TRANSACTION) as? CompositeTransactionModelView ?: return
        bindData(transaction)
        viewModel.getTransactionDetail(transaction.transaction.id)
    }

    override fun viewModel(): BaseViewModel? {
        return viewModel
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_transactiondetail
    }

    override fun initComponents() {
        super.initComponents()
        val container = activity
        if (container is AppCompatActivity) {
            container.setSupportActionBar(toolbar)
            val actionBar = container.supportActionBar ?: return
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
    }

    override fun observe() {
        super.observe()
        viewModel.liveTransactionDetail().observe(this, Observer { resource ->
            progressBar.visibility = View.INVISIBLE
            if (resource != null && !resource.isEmpty()) {
                if (resource.isSuccessful()) {
                    val transaction = resource.data ?: return@Observer
                    bindData(transaction)
                } else if (resource.isLoading()) {
                    progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun bindData(transaction: CompositeTransactionModelView) {
        val context = context ?: return
        textViewTitle.text = if (transaction.isTransfer())
            context.getString(R.string.property_transfer)
        else
            context.getString(R.string.property_issuance)
        textViewAssetName.text = transaction.asset.name
        textViewBlockName.text = transaction.getBlockName(context)
        textViewDescription.text = transaction.getDescription(context)
        textViewBitmarkId.text = transaction.getBitmarkId(context)
        textViewMetaData.text = transaction.getMetadata()
    }
}