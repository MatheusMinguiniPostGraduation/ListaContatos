package br.edu.ifsp.sdm.listacontatossdm.listacontatossdm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsp.sdm.listacontatossdm.R;
import br.edu.ifsp.sdm.listacontatossdm.listacontatossdm.model.Contato;

public class ListaContatosAdapter extends ArrayAdapter<Contato> {

    //Objeto responsável por transformar um xml em uma instância de um objeto
    private LayoutInflater layoutInflater;

    public ListaContatosAdapter(@NonNull Context context, List<Contato> contatoList) {
        super(context, R.layout.layout_view_contato_adapter, contatoList);

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Verificar se célula ainda não existe

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_view_contato_adapter, null);
        }

        TextView nomeContatoTextView =  convertView.findViewById(R.id.nomeContatoTextView);
        TextView emailContatoTextView =  convertView.findViewById(R.id.emailContatoTextView);

        Contato contato = getItem(position);
        nomeContatoTextView.setText(contato.getNome());
        emailContatoTextView.setText(contato.getEmail());

        return convertView;
    }
}
