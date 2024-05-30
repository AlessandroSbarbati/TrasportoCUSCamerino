package it.unicam.cs.gp.CarPooling.Job;

import it.unicam.cs.gp.CarPooling.Service.PrenotazioneService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * Questa classe rappresenta il job per l'aggiornamento delle prenotazioni nel sistema CarPooling.
 */
public class RefreshBookings implements Job {
    /**
     * Esegue il job di aggiornamento delle prenotazioni.
     *
     * @param context il contesto del job
     * @throws JobExecutionException se si verifica un errore durante l'esecuzione del job
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Esecuzione del job RefreshBookings...");
        PrenotazioneService prenotazioneService = (PrenotazioneService) context.getJobDetail().getJobDataMap().get("prenotazioneService");
        prenotazioneService.deleteAllPrenotazioni();
        System.out.println("Job RefreshBookings eseguito con successo.");
    }
}
