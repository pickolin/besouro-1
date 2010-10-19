package besouro.plugin;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Menu;

import besouro.model.Episode;

public class DisagreementPopupMenu {

	private TreeViewer viewer;
	private ProgrammingSession session;
	
	private MenuManager mngr;
	
	private Action nonConformAction;
	private Action conformAction;
	private Action testFirstAction;
	private Action testLastAction;
	private Action testAdditionAction;
	private Action refactoringAction;
	private Action productionAction;
	private Action regressionAction;

	public DisagreementPopupMenu(TreeViewer view, ProgrammingSession session) {
		this.viewer = view;
		this.session = session;
		
		mngr = new MenuManager();
		mngr.setRemoveAllWhenShown(true);
		
		createActions();
		
		mngr.addMenuListener(new IMenuListener() {
			
			public void menuAboutToShow(IMenuManager manager) {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
				if (!selection.isEmpty()) {
					
					Episode episode = (Episode) selection.getFirstElement();
					
					if (episode.isTDD()) {
						mngr.add(nonConformAction);
					} else {
						mngr.add(conformAction);
					}
					
					if (!"test-first".equals(episode.getCategory())) {
						mngr.add(testFirstAction);
					}
					
					if (!"test-last".equals(episode.getCategory())) {
						mngr.add(testLastAction);
					}
					
					if (!"test-addition".equals(episode.getCategory())) {
						mngr.add(testAdditionAction);
					}
					
					if (!"refactoring".equals(episode.getCategory())) {
						mngr.add(refactoringAction);
					}
					
					if (!"production".equals(episode.getCategory())) {
						mngr.add(productionAction);
					}
					
					if (!"regression".equals(episode.getCategory())) {
						mngr.add(regressionAction);
					}
				}
			}
		});
		
	}
	
	private Episode getSelectedEpisode() {
		Episode e = (Episode) ((TreeSelection)viewer.getSelection()).getFirstElement();
		return e;
	}
	
	private void createActions() {
		nonConformAction = new Action(){
			public void run() {
				Episode e = getSelectedEpisode();
				e.setIsTDD(false);
				e.setDisagree(true);
				session.disagreeFromEpisode(e);
				viewer.refresh();
			}
		};
		nonConformAction.setText("non-conformant");
		nonConformAction.setImageDescriptor(Activator.imageDescriptorFromPlugin("besouro_plugin", "icons/episode_nonconformant.png"));
		
		conformAction = new Action(){
			public void run() {
				Episode e = getSelectedEpisode();
				e.setIsTDD(true);
				e.setDisagree(true);
				session.disagreeFromEpisode(e);
				viewer.refresh();
			}

		};
		conformAction.setText("conformant");
		conformAction.setImageDescriptor(Activator.imageDescriptorFromPlugin("besouro_plugin", "icons/episode_conformant.png"));
		
		testFirstAction  = new Action(){
			public void run() {
				Episode e = getSelectedEpisode();
				e.setClassification("test-first", null);
				e.setDisagree(true);
				session.disagreeFromEpisode(e);
				viewer.refresh();
			}
		};
		testFirstAction.setText("test-first");
		
		testLastAction = new Action(){
			public void run() {
				Episode e = getSelectedEpisode();
				e.setClassification("test-last", null);
				e.setDisagree(true);
				session.disagreeFromEpisode(e);
				viewer.refresh();
			}
		};
		testLastAction.setText("test-last");
		
		testAdditionAction = new Action(){
			public void run() {
				Episode e = getSelectedEpisode();
				e.setClassification("test-addition", null);
				e.setDisagree(true);
				session.disagreeFromEpisode(e);
				viewer.refresh();

			}
		};
		testAdditionAction.setText("test-addition");
		
		refactoringAction = new Action(){
			public void run() {
				Episode e = getSelectedEpisode();
				e.setClassification("refactoring", null);
				session.disagreeFromEpisode(e);
				e.setDisagree(true);
				viewer.refresh();

			}
		};
		refactoringAction.setText("refactoring");
		
		productionAction = new Action(){
			public void run() {
				Episode e = getSelectedEpisode();
				e.setClassification("production", null);
				e.setDisagree(true);
				session.disagreeFromEpisode(e);
				viewer.refresh();

			}
		};
		productionAction.setText("production");

		regressionAction = new Action(){
			public void run() {
				Episode e = getSelectedEpisode();
				e.setClassification("regression", null);
				e.setDisagree(true);
				session.disagreeFromEpisode(e);
				viewer.refresh();

			}
		};
		regressionAction.setText("regression");
		
	}
	
	public Menu getMenu() {
		return mngr.createContextMenu(viewer.getControl());
	}
	
}
