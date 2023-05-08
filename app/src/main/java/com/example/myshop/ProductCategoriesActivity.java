package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.adapter.CategoryAdapter;
import com.example.myshop.adapter.ProductAdapter;
import com.example.myshop.domain.Category;
import com.example.myshop.domain.Product;

import java.util.ArrayList;

public class ProductCategoriesActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_categories);

        recyclerViewCategory();
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList=findViewById(R.id.recyclerView2);
        recyclerViewCategoryList.setLayoutManager(LinearLayoutManager);

        ArrayList<Category> category=new ArrayList<>();
        category.add(new Category("Womens", "womens"));
        category.add(new Category("Mens", "mens"));
        category.add(new Category("Jewelery", "jewelery"));
        category.add(new Category("Electronics", "electronics"));

        adapter = new CategoryAdapter(category);
        recyclerViewCategoryList.setAdapter(adapter);
    }
    private void recyclerViewProduct(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProductList=findViewById(R.id.recyclerView1);

        ArrayList<Product> products=new ArrayList<>();
        products.add(new Product("Dress", "dress", "Fancy Yellow Summer Dress", 100.00));
        products.add(new Product("Hat", "hat", "Summer Straw Hat", 35.00));
        products.add(new Product("Jumper", "top", "Knitting Soft Jumper", 75.00));
        products.add(new Product("Shoes", "womanshoes", "White Social Shoes", 120.00));

        adapter2=new ProductAdapter(products);
        recyclerViewProductList.setAdapter(adapter2);
    }
}
