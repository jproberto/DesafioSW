package br.com.joaopaulo.DesafioSW.core.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.joaopaulo.DesafioSW.swapi.cache.PlanetaCache;

@Component
public class TarefasAgendadas {

	private static final long	MULTIPLICADOR_MINUTO	= 60000;
	private static final long	TIMER_EM_MINUTOS		= 60;

	//Chamado a cada hora para limpar o cache de modo que as novas requisições sejam atualizadas
	@Scheduled(fixedRate = TIMER_EM_MINUTOS * MULTIPLICADOR_MINUTO)
	public void limpaCacheQuantidadeAparicoesEmFilmes() {
		PlanetaCache.limpaCache();
	}
}
