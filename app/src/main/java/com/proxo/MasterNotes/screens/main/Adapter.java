package com.proxo.MasterNotes.screens.main;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.proxo.MasterNotes.Main;
import com.proxo.MasterNotes.model.Note;
import com.proxo.MasterNotes.R;

import java.util.List;

import io.reactivex.annotations.NonNull;


public class Adapter extends RecyclerView.Adapter<Adapter.NoteViewHolder>
{
    private SortedList<Note> sortedList;


    public Adapter() 
    {
        sortedList = new SortedList<>(Note.class, new SortedList.Callback<Note>() 
        {
            @Override
            public int compare(Note n1, Note n2) 
            {
                if (n1.done && !n2.done)
                    return 1;
                if (n2.done && !n1.done) 
                    return -1;
                return (int) (n2.timestamp - n1.timestamp);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Note oldItem, Note newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Note item1, Note item2) {
                return item1.uid == item2.uid;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Note> notes) {
        sortedList.replaceAll(notes);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder
    {
        Note note;
        TextView noteText;
        CheckBox completed;
        View delete;
        boolean silentUpdate;

        public NoteViewHolder(@NonNull final View itemView) {
            super(itemView);

            noteText = itemView.findViewById(R.id.note_text);
            completed = itemView.findViewById(R.id.completed);
            delete = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(view -> com.proxo.MasterNotes.screens.main.DetailsActivity.start((Activity) itemView.getContext(), note));

            delete.setOnClickListener(view -> Main.getInstance().getNoteDao().delete(note));

            completed.setOnCheckedChangeListener((compoundButton, checked) ->
            {
                if (!silentUpdate)
                {
                    note.done = checked;
                    Main.getInstance().getNoteDao().update(note);
                }
                updateStrokeOut();
            });

        }

        public void bind(Note note) 
        {
            this.note = note;
            noteText.setText(note.text);
            updateStrokeOut();
            silentUpdate = true;
            completed.setChecked(note.done);
            silentUpdate = false;
        }

        private void updateStrokeOut()
        {
            if (note.done)
                noteText.setPaintFlags(noteText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            else
                noteText.setPaintFlags(noteText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
        
    }
}
