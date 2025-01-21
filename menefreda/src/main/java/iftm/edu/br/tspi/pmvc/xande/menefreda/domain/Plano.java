package iftm.edu.br.tspi.pmvc.xande.menefreda.domain;

public class Plano {
    private Integer codigo;
    private Double valor;
    private String tipo;

    public Plano() {}

    public Plano(Integer codigo) {
        this.codigo = codigo;
    }

    public Plano(Integer codigo, Double valor, String tipo) {
        this.codigo = codigo;
        this.valor = valor;
        this.tipo = tipo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
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
