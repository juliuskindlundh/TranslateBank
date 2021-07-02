package com.example.translatebank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.translatebank.Repository.TranslationDTO;

import java.util.ArrayList;

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.ViewHolder> {

   private ArrayList<TranslationDTO> translationsDTOs;

    public TranslationAdapter(ArrayList<TranslationDTO> translationDTOs) {
        this.translationsDTOs = translationDTOs;
    }

    @Override
    public TranslationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.translation_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TranslationAdapter.ViewHolder holder, int position) {
        holder.getOriginal().setText(translationsDTOs.get(position).getOriginal());
        holder.getTranslation().setText(translationsDTOs.get(position).getResult());
    }

    @Override
    public int getItemCount() {
        return translationsDTOs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView original;
        TextView translation;

       public ViewHolder(View itemView) {
           super(itemView);
            original = itemView.findViewById(R.id.original);
            translation = itemView.findViewById(R.id.translation);
       }


       public TextView getOriginal(){
           return original;
       }

       public TextView getTranslation(){
           return translation;
       }


   }

}
