package skripsi.android.andrian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;



public class Searchpage extends AppCompatActivity {
    ListView listview;
    SearchView search_bar;
    private ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        listview = findViewById(R.id.list);
        search_bar = findViewById(R.id.search_bar);

        List<String> list = new ArrayList<>();
        list.add("Japanese Cherry Blossom");
        list.add("Japanese Cherry Blossom");
        list.add("Skin Care Pack");
        list.add("Make Up Pack For Girls");
        list.add("Best Red Lipstick Shades");
        list.add("Pretty Pink Hair Color");

        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,list);
        listview.setAdapter(arrayAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    startActivity(new Intent(getApplicationContext(),Productdetails.class));
                }
                else if(position == 1)
                {
                    startActivity(new Intent(getApplicationContext(),Productdetails.class));

                }
                else if(position == 2)
                {
                    startActivity(new Intent(getApplicationContext(),productdetails6.class));

                }
                else if(position == 3)
                {
                    startActivity(new Intent(getApplicationContext(),Productdetails3.class));

                }
                else if(position == 4)
                {
                    startActivity(new Intent(getApplicationContext(),productdetails5.class));

                }
                else if(position == 5)
                {
                    startActivity(new Intent(getApplicationContext(),productdetails4.class));

                }
            }
        });

        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                 Searchpage.this.arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Searchpage.this.arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });


    }

    }

