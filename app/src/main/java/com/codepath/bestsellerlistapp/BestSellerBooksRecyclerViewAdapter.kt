package com.codepath.bestsellerlistapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.codepath.bestsellerlistapp.BestSellerBooksRecyclerViewAdapter.BookViewHolder
import com.codepath.bestsellerlistapp.R.id
import com.codepath.bestsellerlistapp.R.layout
import com.codepath.bestsellerlistapp.models.BestSellerBook
import org.w3c.dom.Text

/**
 * [RecyclerView.Adapter] that can display a [BestSellerBook] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class BestSellerBooksRecyclerViewAdapter(
    private val books: List<BestSellerBook>,
    private val mListener: OnListFragmentInteractionListener?
) : Adapter<BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layout.fragment_best_seller_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.mItem = books[position]
        holder.mBookRank.text = books[position].rank.toString()
        Glide.with(holder.mView)
            .load(books[position].bookImageUrl)
            .centerInside()
            .into(holder.mBookImg)
        holder.mBookTitle.text = books[position].title
        holder.mBookAuthor.text = books[position].author
        holder.mBookDescription.text = books[position].description
        holder.mView.setOnClickListener {
            holder.mItem?.let { book ->
                mListener?.onItemClick(book)
            }
        }
        holder.mBookBuyButton.setOnClickListener { v ->
            val uri: Uri = Uri.parse(books[position].amazonUrl)
            val intent: Intent = Intent(Intent.ACTION_VIEW, uri)
            holder.mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }

    inner class BookViewHolder(val mView: View) : ViewHolder(mView) {
        val mBookRank: TextView = mView.findViewById<View>(id.ranking) as TextView
        val mBookImg: ImageView = mView.findViewById<View>(id.book_image) as ImageView
        val mBookTitle: TextView = mView.findViewById<View>(id.book_title) as TextView
        val mBookAuthor: TextView = mView.findViewById<View>(id.book_author) as TextView
        val mBookDescription: TextView = mView.findViewById<View>(id.book_description) as TextView
        val mBookBuyButton: Button = mView.findViewById<View>(id.buy_button) as Button
        var mItem: BestSellerBook? = null
        val mContext: Context = mView.context

        override fun toString(): String {
            return mBookTitle.toString() + " '" + mBookAuthor.text + "'"
        }
    }
}