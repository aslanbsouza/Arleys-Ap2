
package frb.edu.br.aslan.repositorios;

import frb.edu.br.aslan.contratos.IEndereco;
import frb.edu.br.aslan.data.DaoUtil;
import frb.edu.br.aslan.entidades.CidadeDto;
import frb.edu.br.aslan.entidades.EnderecoDto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnderecoRepositorio extends DaoUtil implements IEndereco {
    public EnderecoRepositorio() {
            super();
        }   
    @Override
    public boolean incluir(EnderecoDto endereco) {
        String sql = "INSERT INTO endereco (endereco, endereco2, bairro, cidade_id, cep, telefone ) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps;
         
        int ret=-1;
        try {
            ps = getPreparedStatement(sql);
            ps.setString(1, endereco.getEndereco());
            ps.setString(2, endereco.getEndereco2());
            ps.setString(3, endereco.getBairro());
            ps.setInt(4, endereco.getCidade_id().getCidade_id());
            ps.setString(5, endereco.getCep());
            ps.setString(6, endereco.getTelefone());
            
            ret = ps.executeUpdate();
            ps.close();
                       
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EnderecoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EnderecoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret > 0; 
    } 

    @Override
    public boolean alterar(EnderecoDto endereco) {
        String sql = "UPDATE endereco SET endereco = ?, endereco2 = ?, bairro = ?, cidade_id = ?, cep = ?, telefone = ? WHERE endereco_id=?";
       
        PreparedStatement ps;
        int ret=-1;
        try {
            ps = getPreparedStatement(sql);
            ps.setString(1, endereco.getEndereco());
            ps.setString(2, endereco.getEndereco2());
            ps.setString(3, endereco.getBairro());
            ps.setInt(4, endereco.getCidade_id().getCidade_id());
            ps.setString(5, endereco.getCep());
            ps.setString(6, endereco.getTelefone());

            ret = ps.executeUpdate();
            ps.close();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(EnderecoRepositorio.class.getName()).log(Level.SEVERE, null, e);
        }catch (SQLException ex){
            Logger.getLogger(EnderecoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret >0;
    }

    @Override
     public boolean deletar(int id) {
         String sql = "DELETE FROM endereco " +
                     " WHERE endereco_id=?";
        PreparedStatement ps;
        int ret=-1;
        try {
            ps = getPreparedStatement(sql);
            ps.setInt(1, id);
            ret = ps.executeUpdate();
            ps.close();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(EnderecoRepositorio.class.getName()).log(Level.SEVERE, null, e);
        }catch (SQLException ex){
            Logger.getLogger(EnderecoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret >0;
    }
     
    @Override
  public EnderecoDto getRegistroPorId(int id) {
        EnderecoDto endereco = new EnderecoDto();

        String sql = "SELECT endereco_id, endereco, endereco2, bairro, cidade_id, cep, telefone, ultima_atualizacao FROM endereco WHERE endereco_id=?";

        CidadeRepositorio city = new CidadeRepositorio();
        try {
            PreparedStatement ps = getPreparedStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while ( rs.next() ) {
                endereco = new EnderecoDto(rs.getInt("endereco_id"), 
                        rs.getString("endereco"), 
                        rs.getString("endereco2"),
                        rs.getString("bairro"),
                        city.getRegistroPorId(rs.getInt("cidade_id")),
                        rs.getString("cep"),
                        rs.getString("telefone"),
                        rs.getTimestamp("ultima_atualizacao"));
            }
            rs.close();
            ps.close();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(EnderecoRepositorio.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException ex) {
            Logger.getLogger(EnderecoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return endereco;
    }

    @Override
   public List<EnderecoDto> getListaTodos() {
        List<EnderecoDto> endereco = new LinkedList<EnderecoDto>();

        String sql = "SELECT * FROM endereco";

        CidadeRepositorio cidade = new CidadeRepositorio();

        try {
            PreparedStatement ps = getPreparedStatement(sql);
            ResultSet rs = ps.executeQuery();

            while ( rs.next() ) {

                CidadeDto cid = cidade.getRegistroPorId(rs.getInt("cidade_id"));
                endereco.add( new EnderecoDto( 
                        rs.getInt("endereco_id"),
                        rs.getString("endereco"), 
                        rs.getString("endereco2"),
                        rs.getString("bairro"),
                        cid,
                        rs.getString("cep"),
                        rs.getString("telefone"),
                        rs.getTimestamp("ultima_atualizacao")
                        )
                );
            }
              
            rs.close();
            ps.close();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException ex) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return endereco;
    }
    
}
