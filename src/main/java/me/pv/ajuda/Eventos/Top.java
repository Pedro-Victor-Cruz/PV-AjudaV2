package me.pv.ajuda.Eventos;

import java.util.UUID;

public class Top {

    private String staff;
    private int pontos;

    public Top(String staff, int pontos) {
        this.staff = staff;
        this.pontos = pontos;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

}
