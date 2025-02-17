package iftm.edu.br.tspi.pmvc.xande.menefreda.repository;

import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Medico;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class MedicoRepository {
    private final JdbcTemplate conexao;

    public MedicoRepository(JdbcTemplate conexao){
        this.conexao = conexao;
    }

    public Medico setMedico(ResultSet rs) throws SQLException{
        Medico medico = new Medico();
        medico.setCodigo(rs.getInt("cod_medico"));
        medico.setNome(rs.getString("nome_medico"));
        medico.setArea(rs.getString("area_atuacao"));
        medico.setNumero(rs.getString("tel_medico"));

        return medico;
    }

    public List<Medico> listar(){
        String sql = """
                    select cod_medico,
                        nome_medico,
                        area_atuacao,
                        tel_medico
                    from medico
                    """;
        return conexao.query(sql, (rs, rowNum) -> setMedico(rs));
    }

    public List<Medico> buscaPorNome(String nome) {
        String sql = """
                    select cod_medico,
                        nome_medico,
                        area_atuacao,
                        tel_medico
                    from medico
                    where nome_medico like ?
                    """;
        return conexao.query(sql, (rs, rowNum) -> setMedico(rs), "%" + nome.toLowerCase() + "%");
    }

    public Medico buscaPorCodigo(Integer codigo) {
        String sql = """
                    select cod_medico,
                        nome_medico,
                        area_atuacao,
                        tel_medico
                    from medico
                    where cod_medico = ?
                    """;
        return conexao.queryForObject(sql, (rs, rowNum) -> setMedico(rs), codigo);
    }

    public void salvar(Medico medico){
        String sql = """
                    insert into medico (
                        nome_medico,
                        area_atuacao,
                        tel_medico
                    )
                    values (?,?,?)
                    """;
        conexao.update(sql, medico.getNome(),
                                   medico.getArea(),
                                   medico.getNumero());
    }

    public void atualizar(Medico medico){
        String sql = """
                    update medico
                    set nome_medico = ?,
                        area_atuacao = ?,
                        tel_medico = ?
                    where cod_medico = ?
                    """;
        conexao.update(sql, medico.getNome(),
                            medico.getArea(),
                            medico.getNumero(),
                            medico.getCodigo());
    }

    public void excluir(int codigo){
        String sql = "delete from medico where cod_medico = ?";
        conexao.update(sql, codigo);
    }
}
