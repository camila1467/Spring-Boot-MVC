package curso.springboot.controller;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.model.Telefone;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.repository.TelfoneRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private TelfoneRepository telefoneRepository;

	@RequestMapping(method=RequestMethod.GET, value="/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView modelandView = new ModelAndView ("cadastro/cadastropessoa");

		modelandView.addObject("pessoaObj",new Pessoa());
		return modelandView;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="**/salvarpessoa")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingresult) {
		if (bindingresult.hasErrors())
		
		
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
@PostMapping("**/pesquisarpessoa")
public ModelAndView pesquisar(@RequestParam ("nomepesquisa") String nomepesquisa) {

	ModelAndView andView = new ModelAndView ("cadastro/cadastropessoa");
andView.addObject("pessoas", 	pessoaRepository.findPessoaByName(nomepesquisa));
andView.addObject("pessoaObj",new Pessoa());
return andView;
}
@GetMapping("/telefones/{idpessoa}")
public ModelAndView telefones (@PathVariable("idpessoa")Long idpessoa) {
	
Optional<Pessoa> pessoa= pessoaRepository.findById(idpessoa);
ModelAndView modelandView = new ModelAndView ("cadastro/telefones");

modelandView.addObject("pessoaObj",pessoa.get());

return modelandView;

	}
@PostMapping("**/addfonePessoa/{pessoaid}")
public ModelAndView addFonePessoa( Telefone telefone ,@PathVariable("pessoaid")Long pessoaid) {
	
	Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
	telefone.setPessoa(pessoa);
	telefoneRepository.save(telefone);
	ModelAndView modelandView = new ModelAndView ("cadastro/telefones");
	modelandView.addObject("pessoaObj",pessoa);


	modelandView.addObject("telefones",telefoneRepository.getTelefones(pessoaid));
	return modelandView;
}

@GetMapping("/removertelefone/{idtelefone}")
public ModelAndView removerTelefone (@PathVariable("idtelefone")Long idtelefone) {
	Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();
	telefoneRepository.deleteById(idtelefone);
ModelAndView modelandView = new ModelAndView ("cadastro/telefones");
//findyall carrega todas as pessoas
modelandView.addObject("pessoaObj",pessoa);
modelandView.addObject("telefones",telefoneRepository.getTelefones(pessoa.getId()));




return modelandView;

	}
}
