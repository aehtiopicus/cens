
package ar.com.midasconsultores.catalogodefiltros.service;

public interface TaskSchedulerService {

    /**
     *
     * @throws Exception
     */
    public void deleteExistentTask() throws Exception;

    /**
     *
     * @param copierPath
     * @param fromPath
     * @param toPath
     * @throws Exception
     */
    public int configureNewTask(String copierPath, String fromPath, String toPath) throws Exception;
    
}
