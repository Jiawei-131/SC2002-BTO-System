public class ApplicationController {
    private Project project;
    
    // Constructors ?
    public ApplicationController(Project project) {
		super();
		this.project = project;
	}


    // TBC
	public void showEligibleProjects(Applicant applicant) {
        if (project.getIsVisible() && project.isEligible(applicant)) {
            project.displayProjects();
        }
    }
}