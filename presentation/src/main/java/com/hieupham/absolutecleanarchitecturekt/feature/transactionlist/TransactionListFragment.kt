package com.hieupham.absolutecleanarchitecturekt.feature.transactionlist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import butterknife.BindView
import com.hieupham.absolutecleanarchitecturekt.R
import com.hieupham.absolutecleanarchitecturekt.feature.BaseSupportFragment
import com.hieupham.absolutecleanarchitecturekt.feature.BaseViewModel
import com.hieupham.absolutecleanarchitecturekt.feature.DialogManager
import com.hieupham.absolutecleanarchitecturekt.feature.Navigator
import com.hieupham.absolutecleanarchitecturekt.feature.Navigator.Companion.RIGHT_LEFT
import com.hieupham.absolutecleanarchitecturekt.feature.transactiondetail.TransactionDetailFragment
import com.hieupham.absolutecleanarchitecturekt.model.CompositeTransactionModelView
import com.hieupham.absolutecleanarchitecturekt.util.livedata.Resource
import com.hieupham.absolutecleanarchitecturekt.util.widget.EndlessScrollListener
import javax.inject.Inject

class TransactionListFragment : BaseSupportFragment(), TransactionListAdapter.OnItemClickListener,
        EndlessScrollListener.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    internal lateinit var viewModel: ViewModel

    @Inject
    internal lateinit var navigator: Navigator<TransactionListFragment>

    @Inject
    internal lateinit var dialogManager: DialogManager

    @Inject
    internal lateinit var adapter: TransactionListAdapter

    @BindView(R.id.recycler_transaction)
    internal lateinit var recyclerTransaction: RecyclerView

    @BindView(R.id.progressbar)
    internal lateinit var progressBar: ProgressBar

    @BindView(R.id.layout_swipe)
    internal lateinit var swipeLayout: SwipeRefreshLayout

    private lateinit var endlessScrollListener: EndlessScrollListener

    companion object {
        fun newInstance(): TransactionListFragment {
            return TransactionListFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchLatestTransactions()
    }

    override fun viewModel(): BaseViewModel? {
        return viewModel
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_transactionlist
    }

    override fun initComponents() {
        super.initComponents()
        swipeLayout.setOnRefreshListener(this)
        swipeLayout.setColorSchemeResources(R.color.colorAccent)

        if (recyclerTransaction.itemDecorationCount == 0) {
            recyclerTransaction.addItemDecoration(
                    DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        endlessScrollListener = EndlessScrollListener(this)
        recyclerTransaction.adapter = adapter
        recyclerTransaction.addOnScrollListener(endlessScrollListener)

        adapter.setOnItemClickListener(this)
    }

    override fun observe() {
        super.observe()
        viewModel.nextTransactions().observe(this, observerNextTransactions())
        viewModel.latestTransactions().observe(this, observerLatestTransactions())
        viewModel.refreshedTransactions().observe(this, observerRefreshTransactions())
    }

    override fun onItemClicked(transaction: CompositeTransactionModelView) {
        navigator.anim(RIGHT_LEFT)
                .replaceFragment(R.id.layout_root,
                        TransactionDetailFragment.newInstance(transaction), true)
    }

    override fun onLoadMore() {
        viewModel.getNextTransactions()
    }

    override fun onRefresh() {
        endlessScrollListener.reset()
        viewModel.refreshTransactions()
    }

    private fun observerNextTransactions(): Observer<Resource<List<CompositeTransactionModelView>>> {
        return Observer { resource ->
            progressBar.visibility = View.GONE
            if (resource != null && !resource.isEmpty()) {
                when {
                    resource.isSuccessful() -> {
                        val transactions = resource.data ?: return@Observer
                        if (transactions.isEmpty()) {
                            viewModel.getNextTransactions()
                            return@Observer
                        }
                        adapter.addTransactions(transactions)
                    }
                    resource.isError() -> dialogManager.showError(
                            resource.throwable ?: return@Observer)
                    resource.isLoading() -> progressBar.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun observerLatestTransactions(): Observer<Resource<List<CompositeTransactionModelView>>> {
        return Observer { resource ->
            swipeLayout.isRefreshing = false
            progressBar.visibility = View.GONE
            if (resource != null && !resource.isEmpty()) {
                if (resource.isSuccessful()) {
                    val transactions = resource.data ?: return@Observer
                    if (!transactions.isEmpty()) {
                        adapter.insertTransactions(transactions)
                    }
                } else if (resource.isLoading()) {
                    swipeLayout.isRefreshing = true
                }
            }
        }
    }

    private fun observerRefreshTransactions(): Observer<Resource<List<CompositeTransactionModelView>>> {
        return Observer { resource ->
            swipeLayout.isRefreshing = false
            if (resource != null && !resource.isEmpty()) {
                when {
                    resource.isSuccessful() -> {
                        val transactions = resource.data ?: return@Observer
                        adapter.setTransactions(transactions)
                    }
                    resource.isError() -> dialogManager.showError(
                            resource.throwable ?: return@Observer)
                    resource.isLoading() -> swipeLayout.isRefreshing = true
                }
            }

        }
    }
}