package com.jianglibo.nutchbuilder.katharsis.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.jianglibo.nutchbuilder.katharsis.model.Task;

import io.katharsis.errorhandling.exception.ResourceNotFoundException;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;

@Component
@Validated
public class TaskRepositoryImpl  extends ResourceRepositoryBase<Task, Long> implements TaskRepository {

	private final ProjectRepository projectRepository;
	

	private static final Map<Long, Task> REPOSITORY = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong(4);

	
	@Autowired
    protected TaskRepositoryImpl(ProjectRepository projectRepository) {
		super(Task.class);
		this.projectRepository = projectRepository;
        Task task = new Task(1L, "Create tasks");
        task.setProjectId(123L);
        save(task);
        task = new Task(2L, "Make coffee");
        task.setProjectId(123L);
        save(task);
        task = new Task(3L, "Do things");
        task.setProjectId(123L);
        save(task);
	}
	
	@Override
    public <S extends Task> S save(S entity) {
        if (entity.getId() == null) {
            entity.setId(ID_GENERATOR.getAndIncrement());
        }
        REPOSITORY.put(entity.getId(), entity);
        return entity;
    }
	
	@Override
    public Task findOne(Long taskId, QuerySpec requestParams) {
        Task task = REPOSITORY.get(taskId);
        if (task == null) {
            throw new ResourceNotFoundException("Project not found!");
        }
        if (task.getProject() == null) {
            task.setProject(projectRepository.findOne(task.getProjectId(), new QuerySpec(Task.class)));
        }
        return task;
    }
	
	@Override
    public TasktList findAll(Iterable<Long> taskIds, QuerySpec requestParams) {
        List<Task> foundTasks = new ArrayList<>();
        for (Map.Entry<Long, Task> entry: REPOSITORY.entrySet()) {
            for (Long id: taskIds) {
                if (id.equals(entry.getKey())) {
                    foundTasks.add(entry.getValue());
                }
            }
        }
        TasktList tl = new TasktList();
        tl.addAll(foundTasks);
        return tl;
    }

	@Override
	public TasktList findAll(QuerySpec querySpec) {
		TasktList tl =  new TasktList();
		tl.addAll(REPOSITORY.values());
		return tl;
	}
	
	@Override
    public void delete(Long taskId) {
        REPOSITORY.remove(taskId);
    }

}
