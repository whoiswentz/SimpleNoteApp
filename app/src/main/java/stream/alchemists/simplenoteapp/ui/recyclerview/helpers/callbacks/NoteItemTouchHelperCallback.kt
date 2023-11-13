package stream.alchemists.simplenoteapp.ui.recyclerview.helpers.callbacks

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import stream.alchemists.simplenoteapp.ui.recyclerview.adapters.NoteListAdapter

class NoteItemTouchHelperCallback(
    private val adapter: NoteListAdapter
): ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val movementsSwipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val movementsDragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(movementsDragFlags, movementsSwipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        val initialPosition = viewHolder.adapterPosition
        val finalPosition = target.adapterPosition
        adapter.swap(initialPosition, finalPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        adapter.remove(position)
    }
}