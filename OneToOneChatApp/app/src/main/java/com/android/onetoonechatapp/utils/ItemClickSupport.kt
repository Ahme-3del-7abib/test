package com.android.onetoonechatapp.utils

import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.android.onetoonechatapp.R


class ItemClickSupport {

    private var mRecyclerView: RecyclerView? = null
    private var mOnItemClickListener: AdapterView.OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null

    /*
    private val mOnClickListener: View.OnClickListener =
        View.OnClickListener { v ->
            if (mOnItemClickListener != null) {
                val holder = v?.let { mRecyclerView!!.getChildViewHolder(it) }
                holder?.adapterPosition?.let {
                    mOnItemClickListener.onItemClicked(
                        mRecyclerView,
                        it, v
                    )
                }
            }
        }

    private val mAttachListener: OnChildAttachStateChangeListener =
        object : OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {
                if (mOnItemClickListener != null) {
                    view.setOnClickListener(mOnClickListener)
                }
                if (mOnItemLongClickListener != null) {
                    view.setOnLongClickListener(mOnItemClickListener)
                }
            }

            override fun onChildViewDetachedFromWindow(view: View) {}
        }

     */
    private fun ItemClickSupport(recyclerView: RecyclerView): ItemClickSupport {
        mRecyclerView = recyclerView
        mRecyclerView?.setTag(R.id.item_click_support, this)
        // mRecyclerView?.addOnChildAttachStateChangeListener(mAttachListener)

        return ItemClickSupport()
    }

    fun addTo(view: RecyclerView): ItemClickSupport? {
        return view.getTag(R.id.item_click_support) as ItemClickSupport
    }

    fun removeFrom(view: RecyclerView): ItemClickSupport? {
        val support = view.getTag(R.id.item_click_support) as ItemClickSupport
        support.detach(view)
        return support
    }

    fun setOnItemClickListener(listener: AdapterView.OnItemClickListener): ItemClickSupport? {
        mOnItemClickListener = listener
        return this
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener): ItemClickSupport? {
        mOnItemLongClickListener = listener
        return this
    }

    private fun detach(view: RecyclerView) {
        //  view.removeOnChildAttachStateChangeListener(mAttachListener)
        view.setTag(R.id.item_click_support, null)
    }

    interface OnItemLongClickListener {
        fun onItemLongClicked(recyclerView: RecyclerView?, position: Int, v: View?): Boolean
    }

}