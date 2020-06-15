/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frb.edu.br.aslan.contratos;

import frb.edu.br.aslan.entidades.EnderecoDto;
import java.util.List;

/**
 *
 * @author Aslan
 */
public interface IEndereco {
    boolean incluir(EnderecoDto endereco);
    boolean alterar(EnderecoDto endereco);
    boolean deletar(int id);
    EnderecoDto getRegistroPorId(int id);
    List<EnderecoDto> getListaTodos();
}
