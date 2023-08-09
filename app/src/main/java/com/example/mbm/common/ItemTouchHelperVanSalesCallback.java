package com.example.mbm.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mbm.order.adapter.CartAdapter;
import com.google.android.material.snackbar.Snackbar;

public class ItemTouchHelperVanSalesCallback extends ItemTouchHelper.Callback {

    private final CartAdapter mAdapter;

    public ItemTouchHelperVanSalesCallback(CartAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
//        CardView cv = viewHolder.itemView.findViewById(R.id.cardView);
//        cv.setCardBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.s));
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder != null) {
//            CardView cv = viewHolder.itemView.findViewById(R.id.cardView);
//            cv.setCardBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.purple_200));
        }

    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
//        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//        Log.e("Notify", "onMove");
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();
        mAdapter.onItemRemoved(pos);
        Snackbar snackbar = Snackbar.make(viewHolder.itemView, "Item is removed from the list", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", view -> mAdapter.restoreItem());
        snackbar.show();
    }


}