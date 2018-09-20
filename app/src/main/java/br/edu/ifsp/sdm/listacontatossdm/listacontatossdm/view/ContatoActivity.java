package br.edu.ifsp.sdm.listacontatossdm.listacontatossdm.view;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.edu.ifsp.sdm.listacontatossdm.R;
import br.edu.ifsp.sdm.listacontatossdm.listacontatossdm.model.Contato;

public class ContatoActivity extends AppCompatActivity implements View.OnClickListener{

    private Button botaoSalvar;
    private Button botaoCancelar;

    private EditText nomeEditText;
    private EditText emailEditText;
    private EditText enderecoEditText;
    private EditText telefoneEditText;

    private Integer posicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        botaoSalvar= findViewById(R.id.salvarButton);
        botaoSalvar.setOnClickListener(this);

        botaoCancelar = findViewById(R.id.cancelarButton);
        botaoCancelar.setOnClickListener(this);

        nomeEditText = findViewById(R.id.nomeEditText);
        emailEditText = findViewById(R.id.emailEditText);
        enderecoEditText = findViewById(R.id.enderecoEditText);
        telefoneEditText = findViewById(R.id.telefoneEditText);

        Contato contato = (Contato) getIntent().getSerializableExtra(ListaContatosActivity.CONTATO);

        String subTitulo = "Contato";

        posicao =  (Integer) getIntent().getSerializableExtra(ListaContatosActivity.POSICAO_CLICADA);

        if(contato != null) {

            if(posicao != null){
                subTitulo = "Alterar dados do contato ".concat(contato.getNome());
                modoAlterar(contato);
            }else{
                subTitulo = "Detalhes do contato ".concat(contato.getNome());
                modoDetalhes(contato);
            }
        }

        getSupportActionBar().setSubtitle(subTitulo);

    }

    private void modoAlterar(Contato contato){
        inserirDadosContatoTela(contato);
    }

    private void modoDetalhes(Contato contato){
        inserirDadosContatoTela(contato);

        nomeEditText.setEnabled(Boolean.FALSE);
        emailEditText.setEnabled(Boolean.FALSE);
        telefoneEditText.setEnabled(Boolean.FALSE);
        enderecoEditText.setEnabled(Boolean.FALSE);
        botaoSalvar.setVisibility(View.INVISIBLE);
    }

    private void inserirDadosContatoTela(Contato contato){
        nomeEditText.setText(contato.getNome());
        emailEditText.setText(contato.getEmail());
        telefoneEditText.setText(contato.getTelefone());
        enderecoEditText.setText(contato.getEndereco());
    }

    private Contato salvarContato(){
        Contato contato = new Contato();
        contato.setNome(nomeEditText.getText().toString());
        contato.setTelefone(telefoneEditText.getText().toString());
        contato.setEmail(emailEditText.getText().toString());
        contato.setEndereco(enderecoEditText.getText().toString());

        return contato;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.salvarButton:
                Contato contatoTransacao = salvarContato();

                Intent resultado = new Intent();
                resultado.putExtra(ListaContatosActivity.CONTATO, contatoTransacao);

                if(posicao != null){
                    resultado.putExtra("Posicao clicada", posicao);
                }

                setResult(RESULT_OK, resultado);
                finish();

                break;
            case R.id.cancelarButton:
                setResult(RESULT_CANCELED);
                finish();
                break;

        }

    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
