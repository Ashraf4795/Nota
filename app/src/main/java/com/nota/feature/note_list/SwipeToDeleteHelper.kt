package com.nota.feature.note_list

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nota.R
import com.nota.data.local.room.entity.NoteEntity


class SwipeToDeleteHelper(
    context: Context,
    parent: View,
    private val adapter: NoteListAdapter
) : ItemTouchHelper.Callback() {

    private val mClearPaint: Paint = Paint()
    private val mBackground: ColorDrawable = ColorDrawable()
    private val backgroundColor = Color.parseColor("#b80f0a")
    private val deleteDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.delete_icon)
    private val intrinsicWidth = deleteDrawable?.intrinsicWidth ?: 0
    private val intrinsicHeight = deleteDrawable?.intrinsicHeight ?: 0

    private val snackBar = Snackbar.make(
        parent, R.string.item_swipe_deleted, Snackbar.LENGTH_LONG
    )

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.START)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.height

        val isCancelled = dX.toInt() == 0 && !isCurrentlyActive

        if (isCancelled) {
            clearCanvas(
                c,
                itemView.right + dX,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat()
            )
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        mBackground.setColor(backgroundColor)
        mBackground.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        mBackground.draw(c)

        val deleteIconTop: Int = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin: Int = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft: Int = itemView.right - deleteIconMargin - intrinsicWidth
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom: Int = deleteIconTop + intrinsicHeight


        deleteDrawable?.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteDrawable?.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val itemPosition = viewHolder.adapterPosition
        val listItem = adapter.getData()[itemPosition]

        if (direction == ItemTouchHelper.START) {
            adapter.removeItemAt(itemPosition)
        }
        makeSnackBar(listItem, itemPosition).show()
    }

    private fun clearCanvas(c: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        c.drawRect(left, top, right, bottom, mClearPaint)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.7f
    }

    private fun makeSnackBar(itemSwiped: NoteEntity, itemPosition: Int): Snackbar {
        return snackBar.also {
            it.setAction(R.string.swipe_item_undo_label) {
                adapter.restoreLastDeletedItem(itemSwiped, itemPosition)
            }
            it.setActionTextColor(Color.YELLOW)
        }
    }
}