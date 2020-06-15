
package frb.edu.br.aslan.repositorios;

import frb.edu.br.aslan.contratos.ICidade;
import frb.edu.br.aslan.data.DaoUtil;
import frb.edu.br.aslan.entidades.CidadeDto;
import frb.edu.br.aslan.entidades.PaisDto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CidadeRepositorio  extends DaoUtil implements ICidade{
    
    public CidadeRepositorio() {
        super();
    }    

    @Override
   public boolean incluir(CidadeDto cidade) {

        String sql = "INSERT INTO cidade (cidade, pais_id) VALUES (?, ?) ";
        PreparedStatement ps;
        int ret=-1;
        try {
            ps = getPreparedStatement(sql);
            ps.setString(1, cidade.getCidade());
            ps.setInt(2, cidade.getPais_id().getPais_id()); // se ligar no erro

            ret = ps.executeUpdate();
            ps.close();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, e);
        }catch (SQLException ex){
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret >0;
    }

    @Override
    public boolean alterar(CidadeDto cidade) {
       String sql = "UPDATE cidade SET cidade=?, pais_id=?" +
                     " WHERE cidade_id=?";
       

        PreparedStatement ps;
        int ret=-1;
        try {
            ps = getPreparedStatement(sql);
            ps.setString(1, cidade.getCidade());
            ps.setInt(2, cidade.getPais_id().getPais_id());
          ps.setInt(3, cidade.getCidade_id());

            ret = ps.executeUpdate();
            ps.close();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, e);
        }catch (SQLException ex){
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret >0;
    }

    @Override
    public boolean deletar(int id) {
       String sql = "DELETE FROM cidade " +
                     " WHERE cidade_id=?";
        PreparedStatement ps;
        int ret=-1;
        try {
            ps = getPreparedStatement(sql);
            ps.setInt(1, id);
            ret = ps.executeUpdate();
            ps.close();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, e);
        }catch (SQLException ex){
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret >0;
    }

    @Override
public CidadeDto getRegistroPorId(int id) {
         CidadeDto cidad = new CidadeDto();

        String sql = "SELECT cidade_id, cidade, pais_id, ultima_atualizacao FROM cidade WHERE cidade_id=?";

        PaisRepositorio pais = new PaisRepositorio();
        try {
            PreparedStatement ps = getPreparedStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while ( rs.next() ) {
                cidad = new CidadeDto(rs.getInt("cidade_id"), 
                        rs.getString("cidade"), 
                        pais.getRegistroPorId(rs.getInt("pais_id")),
                        rs.getTimestamp("ultima_atualizacao"));
            }
            rs.close();
            ps.close();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException ex) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cidad;
    }

    @Override
    public List<CidadeDto> getListaTodos() {
      List<CidadeDto> cidad = new LinkedList<>();//mudei para cidad, pode ter erro aqui
 String sql = "SELECT cidade_id, cidade, pais_id,"  +
         "ultima_atualizacao FROM cidade";
 
         PaisRepositorio country = new PaisRepositorio();
         //tem que ter pra puxar o pais
         try {
            PreparedStatement ps = getPreparedStatement(sql);

            ResultSet rs = ps.executeQuery();

        while(rs.next() ){
                PaisDto pais = country.getRegistroPorId(rs.getInt("pais_id"));
                cidad.add( new CidadeDto(rs.getInt("cidade_id"),
                        rs.getString("cidade"),
                            pais,
                        rs.getTimestamp("ultima_atualizacao")
                ));

            }
             rs.close();
             ps.close();

        }  catch (ClassNotFoundException e) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException ex) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

         return cidad;
    }

}
     

