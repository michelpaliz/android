package com.germangascon.navigationdrawersample.Vista.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.germangascon.navigationdrawersample.Interfaz.IOnCorreoSeleccionado;
import com.germangascon.navigationdrawersample.Modelo.Cuenta;
import com.germangascon.navigationdrawersample.R;
import com.germangascon.navigationdrawersample.Vista.Adaptadores.AdaptadorRecibidos;
import com.germangascon.navigationdrawersample.Vista.Adaptadores.AdaptadorSpam;

public class FragmentoSpam extends Fragment {

    private Cuenta cuenta;
    private IOnCorreoSeleccionado iOnCorreoSeleccionado;

    public interface IOnAttachListener{
        Cuenta getCuenta();
    }

    public FragmentoSpam() {
        super(R.layout.recycler_view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AdaptadorSpam adaptadorSpam = new AdaptadorSpam(getContext(),cuenta,iOnCorreoSeleccionado);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptadorSpam);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iOnCorreoSeleccionado = (IOnCorreoSeleccionado) context;
        IOnAttachListener iOnAttachListener = (IOnAttachListener) context;
        cuenta = iOnAttachListener.getCuenta();
    }
}
