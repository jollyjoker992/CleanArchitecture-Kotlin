package com.hieupham.absolutecleanarchitecturekt.feature.transactionlist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Optional
import com.hieupham.absolutecleanarchitecturekt.R
import com.hieupham.absolutecleanarchitecturekt.model.CompositeTransactionModelView

class TransactionListAdapter : RecyclerView.Adapter<TransactionListAdapter.ViewHolder>() {

    private val transactions = mutableListOf<CompositeTransactionModelView>()
    private var listener: OnItemClickListener? = null

    internal fun clear() {
        transactions.clear()
    }

    internal fun setTransactions(transactions: List<CompositeTransactionModelView>) {
        if (transactions.isEmpty()) return
        clear()
        this.transactions.addAll(transactions)
        notifyDataSetChanged()
    }

    internal fun insertTransactions(transactions: List<CompositeTransactionModelView>) {
        if (transactions.isEmpty()) return
        if (this.transactions.isEmpty()) {
            this.transactions.addAll(transactions)
            notifyDataSetChanged()
        } else {
            this.transactions.addAll(0, transactions)
            notifyItemRangeInserted(0, transactions.size)
        }
    }

    internal fun addTransactions(transactions: List<CompositeTransactionModelView>) {
        if (transactions.isEmpty()) return
        if (this.transactions.isEmpty()) {
            this.transactions.addAll(transactions)
            notifyDataSetChanged()
        } else {
            this.transactions.addAll(transactions)
            notifyItemRangeInserted(this.transactions.size, transactions.size)
        }
    }

    internal fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_transactionlist, parent, false)
        return ViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactions[position])
    }


    class ViewHolder(view: View,
            private val listener: OnItemClickListener?) : RecyclerView.ViewHolder(view) {

        private val context: Context = view.context

        private lateinit var transaction: CompositeTransactionModelView

        @BindView(R.id.text_block_name)
        lateinit var textViewBlockName: TextView

        @BindView(R.id.text_asset_name)
        lateinit var textViewAssetName: TextView

        @BindView(R.id.text_description)
        lateinit var textViewDes: TextView

        init {
            ButterKnife.bind(this, view)
        }

        fun bind(transaction: CompositeTransactionModelView) {
            this.transaction = transaction
            textViewBlockName.text = transaction.getBlockName(context)
            textViewDes.text = transaction.getShortDes(context)
            textViewAssetName.text = transaction.asset.name
        }

        @OnClick(R.id.layout_root)
        @Optional
        fun onItemClicked() {
            listener?.onItemClicked(transaction)
        }

    }

    interface OnItemClickListener {
        fun onItemClicked(transaction: CompositeTransactionModelView)
    }
}