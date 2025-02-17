package iftm.edu.br.tspi.pmvc.xande.menefreda.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Dependente;
import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Paciente;

@Repository
public class DependenteRepository {
    private final JdbcTemplate conexao;

    public DependenteRepository(JdbcTemplate conexao) {
        this.conexao = conexao;
    }

    public Dependente setDependente(ResultSet rs) throws SQLException {
        Dependente dependente = new Dependente();
        dependente.setCodigo(rs.getInt("cod_dep"));
        dependente.setCpf(rs.getString("cpf_dep"));
        dependente.setNome(rs.getString("nm_dep"));
        dependente.setGenero(rs.getString("genero_dep"));
        dependente.setDataNascimento(rs.getString("dt_nasc_dep"));
        
        Paciente paciente = new Paciente();
        paciente.setCpf(rs.getString("cpf_paci"));
        paciente.setNome(rs.getString("nome_paci"));
        dependente.setParentesco(rs.getString("parentesco"));
        
        dependente.setTelEmergencia(rs.getString("tel_emergencia"));

        dependente.setPaciente(paciente);
        return dependente;
    }


    public List<Dependente> listar() {
        String sql = """
                    select 
                        cod_dep,
                        cpf_dep,
                        nm_dep,
                        genero_dep,
                        dt_nasc_dep,
                        p.cpf_paci,
                        p.nome_paci,
                        parentesco,
                        tel_emergencia
                    from dependente d, paciente p
                    where d.cpf_paci = p.cpf_paci
                    """;
        return conexao.query(sql, (rs, rowNum) -> setDependente(rs));
    }

    public List<Dependente> buscaPorNomeDep(String nome) {
        String sql = """
                    select 
                        cod_dep,
                        cpf_dep,
                        nm_dep,
                        genero_dep,
                        dt_nasc_dep,
                        d.cpf_paci,
                        nome_paci,
                        parentesco,
                        tel_emergencia
                    from dependente d, paciente p
                    where d.cpf_paci = p.cpf_paci and nm_dep like ?
                    """;
        return conexao.query(sql, (rs, rowNum) -> setDependente(rs),
        "%"+nome.toLowerCase()+"%");
    }
    
    public Dependente buscaPorCpfDep(String cpf) {
        String sql = """
                    select 
                        cod_dep,
                        cpf_dep,
                        nm_dep,
                        genero_dep,
                        dt_nasc_dep,
                        d.cpf_paci,
                        nome_paci,
                        parentesco,
                        tel_emergencia
                    from dependente d, paciente p
                    where d.cpf_paci = p.cpf_paci and cpf_dep like ?
                    """;
        return conexao.queryForObject(sql, (rs, rowNum) -> setDependente(rs), cpf);
    }
    public void salvar(Dependente dependente) {
        String sql = """
                    insert into dependente(
                        cpf_dep,
                        cpf_paci,
                        nm_dep,
                        tel_emergencia,
                        genero_dep,
                        dt_nasc_dep,
                        parentesco
                    )
                    values(?,?,?,?,?,?,?)
                    """;
        conexao.update(sql, dependente.getCpf(),
                            dependente.getPaciente().getCpf(),
                            dependente.getNome(),
                            dependente.getTelEmergencia(),
                            dependente.getGenero(),
                            dependente.getDataNascimento(),
                            dependente.getParentesco());
    }

    public void atualizar(Dependente dependente) {
        String sql = """
                    update dependente
                    set nm_dep = ?,
                        genero_dep = ?,
                        dt_nasc_dep = ?,
                        parentesco = ?,
                        tel_emergencia = ?
                    where cpf_dep = ?
                    """;
        conexao.update(sql, dependente.getNome(),
                            dependente.getGenero(),
                            dependente.getDataNascimento(),
                            dependente.getParentesco(),
                            dependente.getTelEmergencia(),
                            dependente.getCpf());
    }

    public void excluir(String cpf) {
        String sql = "delete from dependente where cpf_dep = ?";
        conexao.update(sql, cpf);
    }
}
