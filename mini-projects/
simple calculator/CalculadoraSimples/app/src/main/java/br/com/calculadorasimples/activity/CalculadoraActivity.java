package br.com.calculadorasimples.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.calculadorasimples.R;


/**
 * Created by diogo_leite on 13/10/17.
 */

public class CalculadoraActivity extends AppCompatActivity {

    private String operador;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_calculadora);
        List<String> opcoes = new ArrayList<String>();
        String [] lista = getResources().getStringArray(R.array.array_operacoes);
        for (String l:lista) {
            opcoes.add(l);
        }
        Spinner spOperacao = (Spinner) findViewById(R.id.spOperacao);
        ArrayAdapter<String> operadores = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,opcoes);
        operadores.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spOperacao.setAdapter(operadores);

        criarEventos();
    }

    private void criarEventos() {



        Spinner spOperacao = (Spinner) findViewById(R.id.spOperacao);
        spOperacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String [] operadores = getResources().getStringArray(R.array.array_operacoes);
                Spinner spOperacao = (Spinner) findViewById(R.id.spOperacao);
                operador = spOperacao.getSelectedItem().toString();
                if(operador.equals(operadores[0])){
                    mudarImagem(R.drawable.ic_nulo);
                }else {
                    if(operador.equals(operadores[1])){
                        mudarImagem(R.drawable.ic_soma);
                    }else {
                        if(operador.equals(operadores[2])){
                            mudarImagem(R.drawable.ic_subtracao);
                        }else {
                            if(operador.equals(operadores[3])){
                                mudarImagem(R.drawable.ic_multiplicacao);
                            }else {
                                if(operador.equals(operadores[4])){
                                    mudarImagem(R.drawable.ic_divisao);
                                }
                            }
                        }
                    }
                }
            }

            private void mudarImagem(int idCaminho){
                ImageView ivOperador = (ImageView) findViewById(R.id.ivOperador);
                ivOperador.setImageResource(idCaminho);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button btnCalcular = (Button) findViewById(R.id.btnCalcular);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!operador.equals(getResources().getStringArray(R.array.array_operacoes)[0])){

                    EditText edtPrimeiroValor = (EditText) findViewById(R.id.edtPrimeroValor);
                    EditText edtSegundoValor = (EditText) findViewById(R.id.edtSegundoValor);

                    if(!edtPrimeiroValor.getText().toString().equals("")  && !edtSegundoValor.getText().toString().equals("")){
                        double primeiroValor = Double.parseDouble(edtPrimeiroValor.getText().toString());
                        double segundoValor = Double.parseDouble(edtSegundoValor.getText().toString());
                        TextView tvResultado = (TextView) findViewById(R.id.tvResultado);
                        try {

                            tvResultado.setText("Resultado: " + Double.toString(calcular(primeiroValor,segundoValor)));
                        } catch (Exception e) {

                            alerta(e.getMessage());
                        }
                    }else{
                        alerta("Algum dos valores está em branco");
                    }
                }else{
                    TextView tvResultado = (TextView) findViewById(R.id.tvResultado);
                    tvResultado.setText("");
                    alerta("Escolha um operador!!");
                }
            }
        });
    }

    private void alerta(String mensagem){
        Toast.makeText(this,mensagem,Toast.LENGTH_LONG).show();
    }

   private double calcular(double primeiroValor,double segundoValor) throws Exception {
       String [] operadores = getResources().getStringArray(R.array.array_operacoes);
       if(operador.equals(operadores[1])){
           return primeiroValor + segundoValor;
       }
       if(operador.equals(operadores[2])){
           return primeiroValor - segundoValor;
       }
       if(operador.equals(operadores[3])){
           return primeiroValor * segundoValor;
       }
       if(operador.equals(operadores[4])){
           if(segundoValor != 0) {
               return primeiroValor / segundoValor;
           }else{
               throw new Exception("Não existe divisão por zero(0)");
           }
       }
       return 0;
   }



}
