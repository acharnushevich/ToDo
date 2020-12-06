package com.academy.services;

import com.academy.comparators.ProjectsNameComparator;
import com.academy.model.ProjectDTO;
import com.academy.model.SearchProjectDTO;
import com.academy.persistence.dao.ProjectDeleteDao;
import com.academy.persistence.entity.Project;
import com.academy.persistence.repositories.ProjectRepository;
import com.academy.persistence.specification.ProjectSpecification;
import com.academy.util.ContentUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectDeleteDao projectDeleteDao;
    private final ProjectRepository projectRepository;
    private final ContentUtils contentUtils;

    public void create(ProjectDTO projectDTO) {
        projectRepository.save(getProject(projectDTO));
    }

    public ProjectDTO findById(Long id) {
        Project project = projectRepository.findById(id).orElse(null);
        return getProjectDto(project);
    }

    public void save(ProjectDTO projectDTO) {
        Project project = projectRepository.findById(projectDTO.getId()).orElse(null);
        if (project != null) {
            project.setName(projectDTO.getName());
            project.setDescription(projectDTO.getDescription());
            projectRepository.save(project);
        }
    }

    public void deleteById(Long id) {
        projectDeleteDao.deleteById(id);
    }

    public boolean existsByName(String name) {
        return projectRepository.existsByName(name);
    }

    public boolean existsByIdNotAndName(Long id, String name) {
        return projectRepository.existsByIdNotAndName(id, name);
    }

    public List<ProjectDTO> findAll() {
        List<Project> projectList = projectRepository.findAll();
        if (!projectList.isEmpty())
            Collections.sort(projectList, new ProjectsNameComparator());
        return getProjectDtoList(projectList);
    }

    public List<ProjectDTO> findAllByProjectsId(String projectsId) {
        List<Project> projectList = new ArrayList<>();
        if (!projectsId.equals("")) {
            for (String itemId : projectsId.split(",")) {
                Project project = projectRepository.findById(Long.parseLong(itemId)).orElse(null);
                if (project != null) {
                    projectList.add(project);
                }
            }
            if (!projectList.isEmpty())
                Collections.sort(projectList, new ProjectsNameComparator());
        }
        return getProjectDtoList(projectList);
    }

    public List<ProjectDTO> findAllSearch(SearchProjectDTO searchProjectDTO) {
        ProjectSpecification projectSpecification = new ProjectSpecification();
        List<Project> projectList = projectRepository.findAll(projectSpecification.findAllSearchSpecification(searchProjectDTO));
        if (!projectList.isEmpty())
            Collections.sort(projectList, new ProjectsNameComparator());
        return getProjectDtoList(projectList);
    }

    private Project getProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(contentUtils.getParam(projectDTO.getName()));
        project.setDescription(contentUtils.getParam(projectDTO.getDescription()));
        return project;
    }

    private ProjectDTO getProjectDto(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(contentUtils.getParam(project.getName()));
        projectDTO.setDescription(contentUtils.getParam(project.getDescription()));
        return projectDTO;
    }

    private List<ProjectDTO> getProjectDtoList(List<Project> projectList) {
        List<ProjectDTO> projectDTOList = new ArrayList<>();
        for (Project project : projectList) {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setId(project.getId());
            projectDTO.setName(contentUtils.getParam(project.getName()));
            projectDTO.setDescription(contentUtils.getParam(project.getDescription()));
            projectDTOList.add(projectDTO);
        }
        return projectDTOList;
    }
}