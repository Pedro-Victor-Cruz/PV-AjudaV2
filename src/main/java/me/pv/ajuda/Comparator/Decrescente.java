package me.pv.ajuda.Comparator;

import me.pv.ajuda.Eventos.Top;

import java.util.Comparator;

public class Decrescente implements Comparator<Top> {
    @Override
    public int compare(Top staff1, Top staff2) {
        if(staff1.getPontos() > staff2.getPontos()){
            return -1;
        }
        return 1;
    }
}
