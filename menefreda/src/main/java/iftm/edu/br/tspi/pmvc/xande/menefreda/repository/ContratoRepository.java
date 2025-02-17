package iftm.edu.br.tspi.pmvc.xande.menefreda.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Contrato;
import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Paciente;
import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Plano;

@Repository
public class ContratoRepository {
    private final JdbcTemplate conexao;

    public ContratoRepository(JdbcTemplate conexao) {
        this.conexao = conexao;
    }

    public Contrato setContrato(ResultSet rs) throws SQLException {
        Contrato contrato = new Contrato();
        contrato.setCodigo(rs.getInt("cod_cont"));
        contrato.setDataContratacao(rs.getString("dt_inicio"));
        contrato.setDataValidade(rs.getString("validade"));
        contrato.setValor(rs.getDouble("valor"));

        Paciente paciente = new Paciente();
        paciente.setCpf(rs.getString("cpf_paci"));
        paciente.setNome(rs.getString("nome_paci"));

        Plano plano = new Plano();
        plano.setTipo(rs.getString("tipo_plano"));

        contrato.setPaciente(paciente);
        contrato.setPlano(plano);
        return contrato;
    }

    public List<Contrato> listar() {
        String sql = """
                    select cod_cont,
                        pa.cpf_paci,
                        pa.nome_paci,
                        pl.tipo_plano,
                        c.dt_inicio,
                        c.validade,
                        c.valor
                    from contrato c, paciente pa, plano pl 
                    where c.cpf_paci = pa.cpf_paci 
                    and c.cod_plano = pl.cod_plano
                    """;
        return conexao.query(sql, (rs, rowNum) -> setContrato(rs));
    }

    public List<Contrato> buscaPorNome(String nome) {
        String sql = """
                    select cod_cont,
                        dt_inicio,
                        validade,
                        valor,
                        pa.cpf_paci,
                        pa.nome_paci,
                        pl.tipo_plano
                    from contrato c, paciente pa, plano pl
                    where c.cpf_paci = pa.cpf_paci
                    and c.cod_plano = pl.cod_plano
                    and pa.nome_paci like ?
                    """;
        return conexao.query(sql, (rs, rowNum) -> setContrato(rs),
        "%"+nome.toLowerCase()+"%");
        }

    public Contrato buscaPorCodigo(Integer codigo) {
        String sql = """
                    select cod_cont,
                        dt_inicio,
                        validade,
                        valor,
                        pa.cpf_paci,
                        pa.nome_paci,
                        pl.tipo_plano
                    from contrato c, paciente pa, plano pl
                    where c.cpf_paci = pa.cpf_paci
                    and c.cod_plano = pl.cod_plano
                    and cod_cont = ?
                    """;
        return conexao.queryForObject(sql, (rs, rowNum) -> setContrato(rs), codigo);
    }

    public Contrato buscaPorCpf(String cpf) {
        String sql = """
                    select cod_cont,
                        dt_inicio,
                        validade,
                        valor,
                        pa.cpf_paci,
                        pa.nome_paci,
                        pl.tipo_plano
                    from contrato c, paciente pa, plano pl
                    where c.cpf_paci = pa.cpf_paci
                    and c.cod_plano = pl.cod_plano
                    and c.cpf_paci like ?
                    """;
        return conexao.queryForObject(sql,
        (rs, rowNum) -> setContrato(rs), cpf);
    }

    public void salvar(Contrato contrato) {
        String sql = """
                    insert into contrato(
                        cpf_paci,
                        cod_plano,
                        dt_inicio,
                        validade,
                        valor
                        )
                    values(?, ?, ?, ?, ?)
                    """;
            conexao.update(sql,
                                contrato.getPaciente().getCpf(),
                                contrato.getPlano().getCodigo(),
                                contrato.getDataContratacao(),
                                contrato.getDataValidade(),
                                contrato.getValor());
    }

    public void atualizar(Contrato contrato) {
        String sql = """
                    update contrato
                    set cpf_paci = ?,
                        cod_plano = ?,
                        dt_inicio = ?,
                        validade = ?,
                        valor = ?
                    where cod_cont = ?
                    """;
        conexao.update(sql, 
                            contrato.getPaciente().getCpf(),
                            contrato.getPlano().getCodigo(),
                            contrato.getDataContratacao(),
                            contrato.getDataValidade(),
                            contrato.getValor(),
                            contrato.getCodigo());
    }

    public void excluir(Integer codigo) {
        String sql = "delete from contrato where cod_cont = ?";
        conexao.update(sql, codigo);
    }
}