//package se.grouprich.projectmanagement.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.transaction.annotation.Transactional;
//
//import se.grouprich.projectmanagement.model.IssueData;
//import se.grouprich.projectmanagement.model.WorkItemData;
//
//public interface IssueRepository extends PagingAndSortingRepository<IssueData, Long>
//{
//	@Query("SELECT i.workItem FROM #{#entityName} i")
//	List<WorkItemData> findWorkItemsHavingIssue();
//
//	@Transactional
//	List<IssueData> removeByWorkItem(WorkItemData workItem);
//}
