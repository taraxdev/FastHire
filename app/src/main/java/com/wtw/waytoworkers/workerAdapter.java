package com.wtw.waytoworkers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class workerAdapter extends RecyclerView.Adapter<workerAdapter.workerViewHolder>{
        private Context mCtx;
        private List<Worker> workerList;
        private OnNoteListner mOnnotelistener;
        private Rating rating;
        private float stars;
        public workerAdapter(Context mCtx, List<Worker> workerList,OnNoteListner onNoteListner) {
                this.mCtx = mCtx;
                this.workerList = workerList;
                this.mOnnotelistener=onNoteListner;
                }

        @NonNull
        @Override
        public workerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(mCtx).inflate(R.layout.listlayout, parent, false);
                return new workerViewHolder(view,mOnnotelistener);
                }

        @Override
        public void onBindViewHolder(@NonNull workerViewHolder holder, int position) {

            Worker worker=workerList.get(position);
            holder.workerName.setText("Name: " + worker.workername);
            holder.workerAddress.setText("Address: " + worker.workeraddress);
            holder.workerrate.setText("INR/hour: " + worker.workerrate);
            holder.ratingBar.setRating(worker.getWorkerrating());
            //LINK
            String driveLink=worker.workerimageurl;
            if (driveLink.length()!=0 && driveLink.length()>=65) {
                String substr = " ";
                substr = driveLink.substring(32, 65);
                String loadUrl = "https://docs.google.com/uc?id=" + substr;
                Glide.with(holder.itemView.getContext()).load(loadUrl).circleCrop().into(holder.workerimage);
            }
            else
            {
                holder.workerimage.setImageResource(R.drawable.nodp);
            }
        }

        @Override
        public int getItemCount() {
                return workerList.size();
                }

        class workerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView workerName, workerAddress, workerrate;
            ImageView workerimage;
            RatingBar ratingBar;
            OnNoteListner onNoteListner;

            public workerViewHolder(@NonNull View itemView,OnNoteListner onNoteListner) {
                super(itemView);

                workerName = itemView.findViewById(R.id.listname);
                workerrate = itemView.findViewById(R.id.listRate);
                workerAddress = itemView.findViewById(R.id.listaddress);
                workerimage=itemView.findViewById(R.id.listpic);
                ratingBar=itemView.findViewById(R.id.listRating);
                this.onNoteListner=onNoteListner;
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                onNoteListner.OnNoteClick(getAdapterPosition());

            }
        }
       public interface OnNoteListner{
            void OnNoteClick(int position);
       }
}
