package it.unicam.cs.gp.CarPooling.Job;

import it.unicam.cs.gp.CarPooling.Service.PrenotazioneService;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Questa classe definisce la configurazione di Quartz Scheduler per il sistema CarPooling.
 */
@Configuration
public class QuartzSchedulerConfig {
    /**
     * Crea il dettaglio del lavoro per il processo di svuotamento delle prenotazioni.
     *
     * @param prenotazioneService il servizio delle prenotazioni
     * @return il dettaglio del lavoro
     */
    @Bean
    public JobDetail prenotazioniSvuotamentoJobDetail(PrenotazioneService prenotazioneService) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("prenotazioneService", prenotazioneService);

        return JobBuilder.newJob(RefreshBookings.class)
                .withIdentity("refreshBookings")
                .storeDurably()
                .usingJobData(jobDataMap)
                .build();
    }
    /**
     * Crea il trigger per il processo di svuotamento delle prenotazioni.
     *
     * @param prenotazioniSvuotamentoJobDetail il dettaglio del lavoro delle prenotazioni
     * @return il trigger per il processo di svuotamento delle prenotazioni
     */
    @Bean
    public Trigger prenotazioniSvuotamentoTrigger(JobDetail prenotazioniSvuotamentoJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(prenotazioniSvuotamentoJobDetail)
                .withIdentity("refreshBookingsTrigger")
                .withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(
                        DateBuilder.SATURDAY, 0, 0))
                .build();
    }
}
