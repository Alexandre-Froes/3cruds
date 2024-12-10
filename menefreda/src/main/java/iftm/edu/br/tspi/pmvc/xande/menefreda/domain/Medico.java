package iftm.edu.br.tspi.pmvc.xande.menefreda.domain;

public class Medico {
    private int codigo;
    private String nome;
    private String area;
    private String numero;

    public Medico() {
    }

    public Medico(int codigo) {
        this.codigo = codigo;
    }

    public Medico(int codigo, String nome, String area, String numero) {
        this.codigo = codigo;
        this.nome = nome;
        this.area = area;
        this.numero = numero;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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
        Medico other = (Medico) obj;
        if (codigo != other.codigo)
            return false;
        return true;
    }
}
