package iftm.edu.br.tspi.pmvc.xande.menefreda.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Paciente;

@Repository
public class PacienteRepository {
    private final JdbcTemplate conexao;

    public PacienteRepository(JdbcTemplate conexao) {
        this.conexao = conexao;
    }

    public Paciente setPaciente(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setCpf(rs.getString("cpf_paci"));
        paciente.setNome(rs.getString("nome_paci"));
        paciente.setGenero(rs.getString("genero_paci"));
        paciente.setEmail(rs.getString("email_paci"));
        paciente.setEndereco(rs.getString("end_paci"));
        paciente.setTelefone(rs.getString("tel_paci"));
        paciente.setDataNascimento(rs.getString("dt_nasc_paci"));

        return paciente;
    }

    public List<Paciente> listar() {
        String sql = """
                    select cpf_paci,
                        nome_paci,
                        genero_paci,
                        email_paci,
                        end_paci,
                        tel_paci,
                        dt_nasc_paci
                    from paciente
                    """;
        return conexao.query(sql, (rs, rowNum) -> setPaciente(rs));
    }

    public List<Paciente> buscaPorNome(String nome) {
        String sql = """
                    select cpf_paci,
                        nome_paci,
                        genero_paci,
                        email_paci,
                        end_paci,
                        tel_paci,
                        dt_nasc_paci
                    from paciente
                    where nome_paci like ?
                    """;
        return conexao.query(sql, (rs, rowNum) -> setPaciente(rs),
        "%"+nome.toLowerCase()+"%");
    }

    public Paciente buscaPorCpf(String cpf) {
        String sql = """
                    select cpf_paci,
                        nome_paci,
                        genero_paci,
                        email_paci,
                        end_paci,
                        tel_paci,
                        dt_nasc_paci
                    from paciente
                    where cpf_paci = ?
                    """;
        return conexao.queryForObject(sql, (rs, rowNum) -> setPaciente(rs), cpf);
    }

    public void salvar(Paciente paciente) {
        String sql ="""
                    insert into paciente(
                        cpf_paci,
                        nome_paci, 
                        genero_paci, 
                        email_paci, 
                        end_paci, 
                        tel_paci, 
                        dt_nasc_paci)

                    values(?, ?, ?, ?, ?, ?, ?)
                    """;
        conexao.update(sql, 
                            paciente.getCpf(),
                            paciente.getNome(),
                            paciente.getGenero(),
                            paciente.getEmail(),
                            paciente.getEndereco(),
                            paciente.getTelefone(),
                            paciente.getDataNascimento());
                            
    }
    public void atualizar(Paciente paciente) {
        String sql = """
                    update paciente
                    set nome_paci = ?,
                        genero_paci = ?,
                        email_paci = ?,
                        end_paci = ?,
                        tel_paci = ?,
                        dt_nasc_paci = ?
                    where cpf_paci = ?
                    """;
        conexao.update(sql, paciente.getNome(),
                            paciente.getGenero(),
                            paciente.getEmail(),
                            paciente.getEndereco(),
                            paciente.getTelefone(),
                            paciente.getDataNascimento(),
                            paciente.getCpf());
    }
    public void excluir(String cpf) {
        String sql = "delete from paciente where cpf_paci = ?";
        conexao.update(sql, cpf);
    }
}
