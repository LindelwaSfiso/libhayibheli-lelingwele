package org.xhanka.biblesiswati.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.xhanka.biblesiswati.R;
import org.xhanka.biblesiswati.ui.main.adapter.BooksAdapter;
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel;

import java.util.Objects;


public class BooksFragment extends Fragment {

    public BooksFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(getActivity()).getApplication())
        ).get(BibleDataBaseViewModel.class);*/

        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int mode = 0;
        if (getArguments() != null)
            mode = getArguments().getInt("testament", 0);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                view.getContext(),
                DividerItemDecoration.VERTICAL)
        );

        BibleViewModel model = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(
                        Objects.requireNonNull(getActivity()).getApplication())
        ).get(BibleViewModel.class);

        BooksAdapter adapter = new BooksAdapter(
                Navigation.findNavController(view),
                model.getTextSizeValue()
        );

        // todo: remove version option
        model.getBooksByMode(mode).observe(this, adapter::submitList);

        recyclerView.setAdapter(adapter);
        // adapter.submitList(viewModel.getBooksForTestament(mode));

/*        recyclerView.setAdapter(new Adapter(
                viewModel.getBooksForTestament(mode),
                Navigation.findNavController(view),
                viewModel.getTextSize()
        ));*/

    }

   /* static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        ArrayList<String> books;
        NavController navController;
        Bundle bundle  = new Bundle();
        int textSize;

        Adapter(ArrayList<String> books, NavController controller, int textSize) {
            this.books = books;
            this.navController = controller;
            this.textSize = textSize;
        }

        @NonNull
        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout._list_item, parent, false),
                    textSize
            );
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
            holder.textView.setText(books.get(position));
            holder.textView.setOnClickListener(v -> {
                bundle.putString("book_name", books.get(position));
                navController.navigate(R.id.action_nav_books_to_nav_chapters, bundle);
            });
        }

        @Override
        public int getItemCount() {
            return books.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            public ViewHolder(@NonNull @NotNull View itemView, int textSize) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);
                ExtensionsKt.setTextSizeSp(textView, textSize);
            }
        }
    }*/
}
