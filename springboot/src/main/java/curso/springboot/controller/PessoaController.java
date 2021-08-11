package curso.springboot.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.repository.PessoaRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	

	@RequestMapping(method=RequestMethod.GET, value="/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView modelandView = new ModelAndView ("cadastro/cadastropessoa");

		modelandView.addObject("pessoaObj",new Pessoa());
		return modelandView;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="**/salvarpessoa")
	public ModelAndView salvar(Pessoa pessoa) {
		pessoaRepository.save(pessoa);
		ModelAndView andView = new ModelAndView ("cadastro/cadastropessoa");
	Iterable<Pessoa>pessoaIt= pessoaRepository.findAll();
	andView.addObject("pessoaObj",new Pessoa());

	andView.addObject("pessoas", pessoaIt);
	return andView;
	}
	@RequestMapping(method=RequestMethod.GET, value="/listapessoas")
	public ModelAndView pessoas() {
		ModelAndView andView = new ModelAndView ("cadastro/cadastropessoa");
	Iterable<Pessoa>pessoaIt= pessoaRepository.findAll();
	andView.addObject("pessoas", pessoaIt);
	andView.addObject("pessoaObj",new Pessoa());

	return andView;
	}
@GetMapping("/editarpessoa/{idpessoa}")
public ModelAndView editar (@PathVariable("idpessoa")Long idpessoa) {
	
Optional<Pessoa> pessoa= pessoaRepository.findById(idpessoa);
ModelAndView modelandView = new ModelAndView ("cadastro/cadastropessoa");

modelandView.addObject("pessoaObj",pessoa.get());

return modelandView;

	}
@GetMapping("/removerpessoa/{idpessoa}")
public ModelAndView remover (@PathVariable("idpessoa")Long idpessoa) {
	
	pessoaRepository.deleteById(idpessoa);
ModelAndView modelandView = new ModelAndView ("cadastro/cadastropessoa");
//findyall carrega todas as pessoas
modelandView.addObject("pessoaObj",pessoaRepository.findAll());
modelandView.addObject("pessoaObj",new Pessoa());




return modelandView;

	}

	
}
