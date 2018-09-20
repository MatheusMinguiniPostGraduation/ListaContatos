package br.edu.ifsp.sdm.listacontatossdm.listacontatossdm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.sdm.listacontatossdm.R;
import br.edu.ifsp.sdm.listacontatossdm.listacontatossdm.adapter.ListaContatosAdapter;
import br.edu.ifsp.sdm.listacontatossdm.listacontatossdm.model.Contato;

public class ListaContatosActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener{

    private final int NOVO_CONTATO_REQUEST_CODE = 0;

    public static final String CONTATO = "CONTATO";
    public static final String POSICAO_CLICADA = "POSICAO_CLICADA";

    //Referencia para as views
    private ListView listaContatosListView;

    private List<Contato> listaContatos;
    private ListaContatosAdapter adapter;

    private Integer posicao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_contatos);

        listaContatosListView = findViewById(R.id.listaContatosListView);

        listaContatos = new ArrayList<>();

        preencherLista();

        adapter = new ListaContatosAdapter(this, listaContatos);

        listaContatosListView.setAdapter(adapter);

        listaContatosListView.setOnItemClickListener(this);

        registerForContextMenu(listaContatosListView);
    }

    private void preencherLista(){
        Integer i = 0;
        while(i < 10){
            this.listaContatos.add(new Contato("MatheusN".concat(i.toString()), "Av. Taquaritinga, 188", "(16) 99706-3747",
                    "minguinimatheus@gmail.com"));
            i++;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return Boolean.TRUE;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_contexto, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo infoMenu = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Contato contato = listaContatos.get(infoMenu.position);

        switch (item.getItemId()){
            case R.id.editarContatoMenuItem:
                editarContato(contato, infoMenu.position);

            case R.id.ligarContatoMenuItem:
                return true;

            case R.id.verEnderecoContatoMenuItem:
                return true;

        }
        return false;
    }

    private void editarContato(Contato contato, int posicao){
        Intent alterarContatoIntent = new Intent(this, ContatoActivity.class);

        alterarContatoIntent.putExtra(ListaContatosActivity.CONTATO, contato);
        alterarContatoIntent.putExtra(POSICAO_CLICADA, posicao);

        startActivityForResult(alterarContatoIntent, NOVO_CONTATO_REQUEST_CODE);
        this.posicao = posicao;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case NOVO_CONTATO_REQUEST_CODE:
                if(resultCode == RESULT_OK){

                    //Recupero o contato da Intent data
                    Contato contato = (Contato) data.getSerializableExtra(CONTATO);

                    if (posicao != null){
                        listaContatos.set(posicao, contato);
                    }

                    else if(contato != null) {
                        listaContatos.add(contato);
                    }

                    Toast.makeText(this, "Opreração realizada com sucesso!", Toast.LENGTH_LONG).show();

                    //Notifico Adapter
                    adapter.notifyDataSetChanged();


                }else{
                    if(resultCode == RESULT_CANCELED){
                        Toast.makeText(this, "Operação cancelada", Toast.LENGTH_LONG).show();
                    }
                }
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.configuracaoMenuItem:
                return true;

            case R.id.novoContatoMenuItem:
                Intent novoContatoIntent = new Intent("NOVO_CONTATO_ACTIVITY");
                startActivityForResult(novoContatoIntent, NOVO_CONTATO_REQUEST_CODE);
                return true;
        }

        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Contato contato = listaContatos.get(position);

        Intent detalhesContatoIntent = new Intent(this, ContatoActivity.class);
        detalhesContatoIntent.putExtra(ListaContatosActivity.CONTATO, contato);
        startActivity(detalhesContatoIntent);
    }
}
