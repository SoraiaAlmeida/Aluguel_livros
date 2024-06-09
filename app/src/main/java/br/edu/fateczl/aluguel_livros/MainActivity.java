package br.edu.fateczl.aluguel_livros;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            loadFragment(bundle);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, new InicioFragment());
            fragmentTransaction.commit();
        }
    }

    private void loadFragment(Bundle bundle) {
        String itemType = bundle.getString("type");

        assert itemType != null;
        switch (itemType) {
            case "aluguel":
                fragment = new AluguelFragment();
                break;
            case "aluno":
                fragment = new AlunoFragment();
                break;
            case "livro":
                fragment = new LivroFragment();
                break;
            default:
                fragment = new RevistaFragment();
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, MainActivity.class);

        if (id == R.id.itemAluguel) {
            bundle.putString("type", "aluguel");
        } else if (id == R.id.itemAluno) {
            bundle.putString("type", "aluno");
        } else if (id == R.id.itemLivro) {
            bundle.putString("type", "livro");
        } else {
            bundle.putString("type", "revista");
        }

        intent.putExtras(bundle);
        this.startActivity(intent);
        this.finish();
        return true;
    }
}