package iftm.edu.br.tspi.pmvc.xande.menefreda.domain;

public class Plano {
    private int codigo;
    private double valor;
    private String tipo;

    public Plano() {}

    public Plano(int codigo) {
        this.codigo = codigo;
    }

    public Plano(int codigo, double valor, String tipo) {
        this.codigo = codigo;
        this.valor = valor;
        this.tipo = tipo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + codigo;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Plano other = (Plano) obj;
        if (codigo != other.codigo)
            return false;
        return true;
    }
}
