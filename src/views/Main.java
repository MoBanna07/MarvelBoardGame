package views;



import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.security.auth.kerberos.KerberosKey;
import javax.swing.LayoutFocusTraversalPolicy;

import engine.Game;
import engine.Player;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.Effect;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Cover;
import model.world.Damageable;
import model.world.Direction;
import model.world.Hero;
import model.world.Villain;








public class Main extends Application {
	
	private Stage primaryStage;
	private Scene scene1;
	private Scene scene2;
	private Scene scene3;
	private Scene scene4;
	private Scene scene5;
	private Scene scene6;
	
	private Game g;
	
	private Label current = new Label();
	private Player first;
	private Player second;
	
	private DropShadow shadow = new DropShadow();
	
	private Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	
	
	private HBox firstteam = new HBox();
	private HBox secondteam = new HBox();
	
	private Button Ability1;
	private Button Ability2;
	private Button Ability3;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
				primaryStage.setTitle("Game");
		primaryStage.setMaximized(true);
		primaryStage.setResizable(false);
		
		scene1 = startscene1(primaryStage);
		
		primaryStage.setScene(scene1);
		
		
		
		
		
		primaryStage.show();
		
	}
	
	public Scene startscene1 (Stage primarystage) {
		
		primaryStage = primarystage;
		Label names = new Label("Please Enter Players names");
		Label firstplayer = new Label("Please Enter First player's name");
		Label secondplayer = new Label("Please Enter Second player's name");
		
		TextField firstPlayerName = new TextField();
		firstPlayerName.setPromptText("first player");
		
		TextField secondPlayerName = new TextField();
		secondPlayerName.setPromptText("second player");
		
		
		Button choose = new Button("Choose your champions");	
		//initiate instance of game with entered names
		//setting the function of the button
		choose.setOnAction(e -> {
			if(!firstPlayerName.getText().equals("") && !secondPlayerName.getText().equals("")) {
				first = new Player(firstPlayerName.getText());
				second = new Player(secondPlayerName.getText());
				g = new Game(first, second);
				//switching to scene 2
				scene2 = startscene2(primaryStage,first,second);
				primaryStage.setScene(scene2);
			}
			else {
				Alert alert1 = new Alert(AlertType.WARNING, "Please enter a name", ButtonType.CLOSE);
				alert1.show();
			}
		});
		//make button enter button
		choose.setDefaultButton(true);
		
		HBox nlayout = new HBox();
		nlayout.setPadding(new Insets(10));
		nlayout.setAlignment(Pos.CENTER);
		
		HBox chooselayout = new HBox();
		chooselayout.setPadding(new Insets(10));
		chooselayout.setAlignment(Pos.CENTER);
		
		VBox fplayout = new VBox();
		fplayout.setPadding(new Insets(10));
		fplayout.setAlignment(Pos.CENTER);
		
		
		VBox splayout = new VBox();
		splayout.setPadding(new Insets(10));
		splayout.setAlignment(Pos.CENTER);
		
		
		//getting layouts in scene
		nlayout.getChildren().add(names);
		chooselayout.getChildren().add(choose);
		fplayout.getChildren().addAll(firstplayer, firstPlayerName);
		splayout.getChildren().addAll(secondplayer, secondPlayerName);
		
		BorderPane layout = new BorderPane();
		layout.setTop(nlayout);
		layout.setLeft(fplayout);
		layout.setRight(splayout);
		layout.setBottom(chooselayout);
		
		Scene scene = new Scene(layout, screenSize.getWidth(), screenSize.getHeight());
		return scene;
	}
	
	public Scene startscene2 (Stage primaryStage, Player first, Player second) {
		//loading abilities and champs from csv
		try{
			Game.loadAbilities("Abilities.csv");
			Game.loadChampions("Champions.csv");
		}catch (IOException e1) {
			System.out.print(e1.getMessage());
		}
		
		
		Label choosechamps = new Label(first.getName() + ", Please Choose your Champions!");
		BorderPane layout = new BorderPane();
		layout.setTop(choosechamps);
		
		FlowPane flow = new FlowPane();
	    flow.setPadding(new Insets(5, 0, 5, 0));
	    flow.setVgap(4);
	    flow.setHgap(4);
	    flow.setPrefWrapLength(255); // preferred width allows for two columns
	    flow.setStyle("-fx-background-color: DAE6F3;");
	    flow.setAlignment(Pos.CENTER);

	    //showing the champions to choose from
	    for(Champion c : engine.Game.getAvailableChampions()) {
	    	Button Champ = new Button(c.getName());
	    	info(Champ,c);
	    	Champ.setOnAction(e -> {
	    		if(first.getTeam().size() < 3) {
	    			first.getTeam().add(c);
	    			Champ.setDisable(true);
	    				if(first.getTeam().size() == 2) {
	    					choosechamps.setText(second.getName() + ", Please Choose your Champions!");
	    				}
	    		}
	    		else if(second.getTeam().size() < 3) {
	    				second.getTeam().add(c);
	    				Champ.setDisable(true);
	    			if(second.getTeam().size() == 3) {
	    				g = new Game(first,second);
	    				scene3 = startscene3(primaryStage);
	    				primaryStage.setScene(scene3);
	    			}
	    		}
	    		

	    	});
			flow.getChildren().add(Champ);
		}

		
		layout.setCenter(flow);
		Scene scene = new Scene(layout, screenSize.getWidth(), screenSize.getHeight());
		return scene;
	}
	
	public Scene startscene3 (Stage primaryStage) {
		//choosing the leaders
		Label label1 = new Label(g.getFirstPlayer().getName() + ", Please Choose Your Leader");
		Label label2 = new Label(g.getSecondPlayer().getName() + ", Please Choose Your Leader");
		
		Button champ1 = new Button(g.getFirstPlayer().getTeam().get(0).getName());
		info(champ1,g.getFirstPlayer().getTeam().get(0));
		Button champ2 = new Button(g.getFirstPlayer().getTeam().get(1).getName());
		info(champ2,g.getFirstPlayer().getTeam().get(1));
		Button champ3 = new Button(g.getFirstPlayer().getTeam().get(2).getName());
		info(champ3,g.getFirstPlayer().getTeam().get(2));
		
		Button champ4 = new Button(g.getSecondPlayer().getTeam().get(0).getName());
		info(champ4,g.getSecondPlayer().getTeam().get(0));
		Button champ5 = new Button(g.getSecondPlayer().getTeam().get(1).getName());
		info(champ5,g.getSecondPlayer().getTeam().get(1));
		Button champ6 = new Button(g.getSecondPlayer().getTeam().get(2).getName());
		info(champ6,g.getSecondPlayer().getTeam().get(2));
		
		Button startGame = new Button("Start Game");
		//switching to scene3
		startGame.setOnAction(e-> {
			if(g.getFirstPlayer().getLeader() != null && g.getSecondPlayer().getLeader() != null) {
				
				scene4 = startscene4(primaryStage);
				primaryStage.setScene(scene4);
				
			}
			else {
				Alert alert1 = new Alert(AlertType.WARNING, "Please choose a leader", ButtonType.CLOSE);
				alert1.show();
			}
		});
		startGame.setAlignment(Pos.CENTER);
		
		champ1.setOnAction(e -> {
			g.getFirstPlayer().setLeader(g.getFirstPlayer().getTeam().get(0));
			champ2.setDisable(true);
			champ3.setDisable(true);
		});
		champ2.setOnAction(e -> {
			g.getFirstPlayer().setLeader(g.getFirstPlayer().getTeam().get(1));
			champ1.setDisable(true);
			champ3.setDisable(true);
		});
		champ3.setOnAction(e -> {
			g.getFirstPlayer().setLeader(g.getFirstPlayer().getTeam().get(2));
			champ1.setDisable(true);
			champ2.setDisable(true);
		});
		champ4.setOnAction(e -> {
			g.getSecondPlayer().setLeader(g.getSecondPlayer().getTeam().get(0));
			champ5.setDisable(true);
			champ6.setDisable(true);
		});
		champ5.setOnAction(e -> {
			g.getSecondPlayer().setLeader(g.getSecondPlayer().getTeam().get(1));
			champ4.setDisable(true);
			champ6.setDisable(true);
		});
		champ6.setOnAction(e -> {
			g.getSecondPlayer().setLeader(g.getSecondPlayer().getTeam().get(2));
			champ4.setDisable(true);
			champ5.setDisable(true);
		});
		
		VBox p1 = new VBox();
		p1.getChildren().addAll(label1, champ1, champ2, champ3);
		label1.setAlignment(Pos.CENTER);
		
		VBox p2 = new VBox();
		p2.getChildren().addAll(label2, champ4, champ5, champ6);
		label2.setAlignment(Pos.CENTER);
		
		
		BorderPane New = new BorderPane();
		
		New.setLeft(p1);
		New.setRight(p2);
		New.setCenter(startGame);
		scene3 = new Scene(New, screenSize.getWidth(), screenSize.getHeight());
		return scene3;
	}
	//hover on champions buttons
	public void info(Button Champ , Champion c) {

		String s = "";
		if(c instanceof Hero) {
			s = "Hero \n";
		}
		else if(c instanceof Villain) {
			s = "Villian \n";
		}
		else if(c instanceof AntiHero) {
			s = "AntiHero \n";
		}
		String ap = "applied effects :";
		if(c.getAppliedEffects().isEmpty())
			ap += "None";
		else
			for(Effect e : c.getAppliedEffects()) {
				ap += "name : " + e.getName() + "\n";
				ap += "duration : " + e.getDuration(); 
			}
		String l = "not leader";
		if(g.getFirstPlayer().getLeader() != null &&  g.getFirstPlayer().getLeader().equals(c))
			l = g.getFirstPlayer().getName() + "'s leader";
		else if(g.getFirstPlayer().getLeader() != null && g.getSecondPlayer().getLeader().equals(c))
			l = g.getSecondPlayer().getName() + "'s leader";
		String team = "no team yet";	
		if(g.getFirstPlayer().getTeam().contains(c))
			team = "team : first team";
		else if(g.getSecondPlayer().getTeam().contains(c))
			team = "team : second team";
		Champ.setTooltip(new Tooltip(s + 
				c.getName() + "\n" +
				team + "\n" +
				 l + "\n" +
				"ability 1 : " + c.getAbilities().get(0).getName() + "\n" +
				"ability 2 : " + c.getAbilities().get(1).getName() + "\n" +
				"ability 3 : " + c.getAbilities().get(2).getName() +  "\n" +
				ap + "\n" +
				"current action points : " + c.getCurrentActionPoints() + "\n" +
				"maximum action point : " + c.getMaxActionPointsPerTurn() +
				"attack damage : " + c.getAttackDamage() + "\n" +
				"attack range : " + c.getAttackRange() + "\n" +
				"HP : " + c.getCurrentHP() + "\n" +
				"mana : " + c.getMana() + "\n" +
				"speed : " + c.getSpeed()
				));
						

	}
	
	public Scene startscene4 (Stage primaryStage) {
		
		Label currentchamp = new Label();
		setcurrent(currentchamp);
		
		
		Label firstleaderability = new Label("first leader ability is not used yet");
		Label secondleaderability = new Label("second leader ability is not used yet");
		
		VBox leaderability = new VBox();
		leaderability.setAlignment(Pos.CENTER);
		leaderability.getChildren().addAll(firstleaderability,secondleaderability);
		
		
		
		GridPane board = new GridPane();
		//Setting size for the pane  
		board.setMinSize(400, 200); 

		//Setting the padding  
		board.setPadding(new Insets(10, 10, 10, 10)); 

		//Setting the vertical and horizontal gaps between the columns 
		board.setVgap(5); 
		board.setHgap(5);
		
		g.placeChampions();
		
		VBox teams = new VBox();
		
		setteams();
		
		Label first = new Label(g.getFirstPlayer().getName() + "'s team : ");
		
		
		Label second = new Label(g.getSecondPlayer().getName() + "'s team : ");
		
		
		
		teams.getChildren().addAll(first,firstteam,second,secondteam);
		teams.setAlignment(Pos.CENTER);
		
//		for(Object o : g.getBoard()) {
//			if(o != null && o instanceof Champion)
//				System.out.println(((Champion)o).getName());
//				
//		}
		current();
		
		ArrayList<Damageable> wholeboard = new ArrayList<>();
		for(int i = 0; i < Game.getBoardwidth() ; i++) {
			for(int j = 0; j < Game.getBoardheight() ; j++) {
				if(g.getBoard()[i][j] != null) {
					wholeboard.add((Damageable)g.getBoard()[i][j]);
				}
			}
		}
		//making the visualized board using grid pane
		for(int i = 0 ;i <= 4 ; i++) {
			
			
			for(int j = 0;j <= 4 ; j++) {
				
				if(g.getBoard()[i][j]!=null) {
					if(g.getBoard()[i][j] instanceof Cover) {
						Button cell = new Button("cover");
						cell.setMinSize(100, 100);
						cell.setStyle("-fx-background-color: #000000");
						cell.setTooltip(new Tooltip("" + ((Cover)g.getBoard()[i][j]).getCurrentHP()));
						board.add(cell, j, 4-i);
					}
					else if(g.getBoard()[i][j] instanceof Champion) {
//						Button cell = new Button(((Champion)g.getBoard()[i][j]).getName());
//						info(cell,(Champion)g.getBoard()[i][j]);
//						cell.setStyle("-fx-border-color: #ff0000");
//						cell.setStyle("-fx-background-color: #0000FF");
//						cell.setMinSize(100, 100);
//						board.add(cell, j, 4-i);
						if(g.getFirstPlayer().getTeam().contains((Champion)g.getBoard()[i][j])) {
							Button cell = new Button(((Champion)g.getBoard()[i][j]).getName());
							info(cell,(Champion)g.getBoard()[i][j]);
							cell.setStyle("-fx-background-color: #0000FF");
							cell.setMinSize(100, 100);
							if(g.getFirstPlayer().getLeader().equals(g.getBoard()[i][j]))
								cell.setStyle("-fx-background-color: #d8bb78");
							firstteam.getChildren().add(cell);
							board.add(cell, j, 4-i);
						}
						else if(g.getSecondPlayer().getTeam().contains((Champion)g.getBoard()[i][j])) {
							Button cell = new Button(((Champion)g.getBoard()[i][j]).getName());
							info(cell,(Champion)g.getBoard()[i][j]);
							cell.setStyle("-fx-background-color: #FF0000");
							cell.setMinSize(100, 100);
							if(g.getSecondPlayer().getLeader().equals(g.getBoard()[i][j]))
								cell.setStyle("-fx-background-color: #1018");
							secondteam.getChildren().add(cell);
							board.add(cell, j, 4-i);
						}
					}
				}
				else if(g.getBoard()[i][j]==null) {
					Button cell = new Button(""); 
					cell.setMinSize(100, 100);
					board.add(cell, j, 4-i);
				}
				
			}
		}
		
		
		// move buttons
		Button moveup = new Button("move up");
//		scene4.setOnKeyPressed(ev -> {
//	        if (ev.getCode() == KeyCode.W) {
//	        	moveup.fire();
//	        	ev.consume(); 
//	        }
//	    });
		Button movedown = new Button("move down");
//		scene4.setOnKeyPressed(ev -> {
//	        if (ev.getCode() == KeyCode.S) {
//	        	movedown.fire();
//	        	ev.consume(); 
//	        }
//	    });
		Button moveright = new Button("move right");
//		scene4.setOnKeyPressed(ev -> {
//	        if (ev.getCode() == KeyCode.D) {
//	        	moveright.fire();
//	        	ev.consume(); 
//	        }
//	    });
		Button moveleft = new Button("move left");	
//		scene4.setOnKeyPressed(ev -> {
//	        if (ev.getCode() == KeyCode.A) {
//	        	moveleft.fire();
//	        	ev.consume(); 
//	        }
//	    });
//		
		moveup.setOnAction(e -> {
			move(board,Direction.UP);
		});
		movedown.setOnAction(e -> {
			move(board,Direction.DOWN);
		});
		moveright.setOnAction(e -> {
			move(board,Direction.RIGHT);
		});
		moveleft.setOnAction(e -> {
			move(board,Direction.LEFT);
		});
		
		HBox move = new HBox();
		move.getChildren().addAll(moveup,movedown,moveright,moveleft);
		
		//attack buttons
		Button Attackup = new Button("Attack Up");
		Button Attackdown = new Button("Attack Down");
		Button Attackright = new Button("Attack Right");
		Button Attackleft = new Button("Attack Left");
		//attack button function
		Attackup.setOnAction(e -> {
			Attack(Direction.UP);
			cleanup(board,wholeboard);
		});
		Attackdown.setOnAction(e -> {
			Attack(Direction.DOWN);
			cleanup(board,wholeboard);
		});
		Attackright.setOnAction(e -> {
			Attack(Direction.RIGHT);
			cleanup(board,wholeboard);
		});
		Attackleft.setOnAction(e -> {
			Attack(Direction.LEFT);
			cleanup(board,wholeboard);
		});
		
		HBox Attack = new HBox();
		Attack.getChildren().addAll(Attackup, Attackdown, Attackright, Attackleft);
		
		
		
		//ability buttons
		Ability1 = new Button(g.getCurrentChampion().getAbilities().get(0).getName());
		abilityinfo(Ability1,1);
		Ability2 = new Button(g.getCurrentChampion().getAbilities().get(1).getName());
		abilityinfo(Ability2,2);
		Ability3 = new Button(g.getCurrentChampion().getAbilities().get(2).getName());
		abilityinfo(Ability3,3);
		
		//ability button functions
		Ability1.setOnAction(e -> {
			Ability(g.getCurrentChampion().getAbilities().get(0));
			
			cleanup(board,wholeboard);
		});
		Ability2.setOnAction(e -> {
			Ability(g.getCurrentChampion().getAbilities().get(1));
			cleanup(board,wholeboard);
		});
		Ability3.setOnAction(e -> {
			Ability(g.getCurrentChampion().getAbilities().get(2));
			cleanup(board,wholeboard);
		});
		
		HBox Ability = new HBox();
		Ability.getChildren().addAll(Ability1,Ability2,Ability3);
		Ability.setAlignment(Pos.CENTER);
		
		Button UseLeaderAbility = new Button("Use Leader Ability");
		UseLeaderAbility.setOnAction(e -> {
			try {
				g.useLeaderAbility();
				leaderabilityused(firstleaderability, secondleaderability);
				cleanup(board,wholeboard);
			} catch (LeaderNotCurrentException e1) {
				Alert alert = new Alert(AlertType.ERROR, e1.getMessage() , ButtonType.CLOSE);
				alert.show();
			} catch (LeaderAbilityAlreadyUsedException e1) {
				Alert alert = new Alert(AlertType.ERROR, e1.getMessage() , ButtonType.CLOSE);
				alert.show();
			}
		});
		//endturn
		Button endturn = new Button("end turn");
		endturn.setOnAction(e1 -> {
			try {
				g.endTurn();
				
				
				current();
				
				setteams();
				
				abilityinfo(Ability1, 1);
				abilityinfo(Ability2, 2);
				abilityinfo(Ability3, 3);
				
				
				
				leaderabilityinfo(UseLeaderAbility);
				setcurrent(currentchamp);
				
			}catch (Exception e){
				for(Champion c : g.getFirstPlayer().getTeam()) {
					g.getTurnOrder().insert(c);
				}
				for(Champion c : g.getSecondPlayer().getTeam()) {
					g.getTurnOrder().insert(c);
				}
				System.out.print(g.getTurnOrder().size());
				Alert alert = new Alert(AlertType.ERROR, e.getMessage() , ButtonType.CLOSE);
				alert.show();
			}
		});
		
		
		
		
		
		VBox right = new VBox();
		right.getChildren().addAll(current,move,Attack,Ability,UseLeaderAbility,endturn);
		right.setAlignment(Pos.CENTER);
		board.setAlignment(Pos.CENTER);
		BorderPane game = new BorderPane();
		
		
		game.setRight(right);
		
		game.setCenter(board);
		scene4 = new Scene(game, screenSize.getWidth(), screenSize.getHeight());
		scene4.setOnKeyPressed(ev -> {
	        if (ev.getCode() == KeyCode.W) {
	        	moveup.fire();
	        	ev.consume(); 
	        }
	    });
		
		VBox left = new VBox();
		left.getChildren().addAll(currentchamp,teams);
		
		game.setLeft(left);
		
		game.setTop(leaderability);
		
		scene4.setOnKeyPressed(ev -> {
	        if (ev.getCode() == KeyCode.A) {
	        	moveleft.fire();
	        	ev.consume(); 
	        }
	    });
		scene4.setOnKeyPressed(ev -> {
	        if (ev.getCode() == KeyCode.S) {
	        	movedown.fire();
	        	ev.consume(); 
	        }
	    });
		scene4.setOnKeyPressed(ev -> {
			if (ev.getCode() == KeyCode.W) {
	        	moveup.fire();
	        	ev.consume(); 
	        }
			if (ev.getCode() == KeyCode.S) {
	        	movedown.fire();
	        	ev.consume(); 
	        }
	        if (ev.getCode() == KeyCode.D) {
	        	moveright.fire();
	        	ev.consume(); 
	        }
	        if (ev.getCode() == KeyCode.A) {
	        	moveleft.fire();
	        	ev.consume(); 
	        }
	    });
		
		
		
		return scene4;
		
		
		
		
		
	}
	private void move(GridPane board, Direction d) {
		try {
			int x1 = (int)g.getCurrentChampion().getLocation().getX();
			int y1 = (int)g.getCurrentChampion().getLocation().getY();	
			int x = (int)g.getCurrentChampion().getLocation().getY();
			int y = (4-(int)g.getCurrentChampion().getLocation().getX());
			Button emptycell = new Button("");
			emptycell.setMinSize(100, 100);
			g.move(d);
			board.add(emptycell , x , y);
			ObservableList<Node> children = board.getChildren();
			for(Node node : children) {
			    if(GridPane.getRowIndex(node) == y && GridPane.getColumnIndex(node) == x) {
			    	board.getChildren().remove(node);
			        break;
			    }
			  }
			for(Node node : children) {
			    if(GridPane.getRowIndex(node) == 4-(int)g.getCurrentChampion().getLocation().getX() && GridPane.getColumnIndex(node) == (int)g.getCurrentChampion().getLocation().getY()) {
			    	board.getChildren().remove(node);
			        break;
			    }
			  }
			Button cell = new Button(g.getCurrentChampion().getName());
			if(g.getFirstPlayer().getTeam().contains(g.getCurrentChampion())) {
				cell = new Button(g.getCurrentChampion().getName());
				info(cell,g.getCurrentChampion());
				cell.setStyle("-fx-background-color: #0000FF");
				cell.setMinSize(100, 100);
				
				firstteam.getChildren().add(cell);
				board.add(cell, (int)g.getCurrentChampion().getLocation().getY(), 4-(int)g.getCurrentChampion().getLocation().getX());
			}
			else if(g.getSecondPlayer().getTeam().contains(g.getCurrentChampion())) {
				cell = new Button(g.getCurrentChampion().getName());
				info(cell,g.getCurrentChampion());
				cell.setStyle("-fx-background-color: #FF0000");
				cell.setMinSize(100, 100);
				secondteam.getChildren().add(cell);
				board.add(cell, (int)g.getCurrentChampion().getLocation().getY(), 4-(int)g.getCurrentChampion().getLocation().getX());
			}
			
			
		} catch (UnallowedMovementException e1) {
			Alert alert = new Alert(AlertType.ERROR, e1.getMessage() , ButtonType.CLOSE);
			alert.show();
		} catch (NotEnoughResourcesException e1) {
			Alert alert = new Alert(AlertType.ERROR,  e1.getMessage() , ButtonType.CLOSE);
			alert.show();
			
		}
	}
	public void Attack(Direction d) {
		try {
			g.attack(d);
		} catch (NotEnoughResourcesException e1) {
			Alert alert = new Alert(AlertType.ERROR, e1.getMessage() , ButtonType.CLOSE);
			alert.show();
		} catch (ChampionDisarmedException e1) {
			Alert alert = new Alert(AlertType.ERROR, e1.getMessage() , ButtonType.CLOSE);
			alert.show();
		} catch (InvalidTargetException e1) {
			Alert alert = new Alert(AlertType.ERROR, e1.getMessage() , ButtonType.CLOSE);
			alert.show();
		}
		
	}
	
	public void abilityinfo(Button ability, int x) {
		String s = "";
		if(g.getCurrentChampion().getAbilities().get(x-1) instanceof DamagingAbility) {
			s += "ability type : Damaging Abililty " +  "\n" +
					"damage amount : " + ((DamagingAbility)g.getCurrentChampion().getAbilities().get(x-1)).getDamageAmount();
		}
		else if(g.getCurrentChampion().getAbilities().get(x-1) instanceof HealingAbility) {
			s += "ability type : Healing Abililty " +  "\n" +
					"heal amount : " + ((HealingAbility)g.getCurrentChampion().getAbilities().get(x-1)).getHealAmount();
		}
		else  {
			s += "ability type : Crowd control Abililty " + "\n" +
					"effect name : " + ((CrowdControlAbility)g.getCurrentChampion().getAbilities().get(x-1)).getEffect().getName() + "\n" +
					"effect duration : " +((CrowdControlAbility)g.getCurrentChampion().getAbilities().get(x-1)).getEffect().getDuration();
		}
		
		
		
		ability.setTooltip(new Tooltip(
				"name : " + g.getCurrentChampion().getAbilities().get(x-1).getName() + "\n" +
				s +	"\n" +	
				"cast area : " + g.getCurrentChampion().getAbilities().get(x-1).getCastArea() + "\n" +		
				"current cooldown : " + g.getCurrentChampion().getAbilities().get(x-1).getCurrentCooldown() + "\n" +
				"base cooldown : " + g.getCurrentChampion().getAbilities().get(x-1).getBaseCooldown() + "\n" +
				"cast range :" + g.getCurrentChampion().getAbilities().get(x-1).getCastRange() + "\n" +
				"mana cost : " + g.getCurrentChampion().getAbilities().get(x-1).getManaCost() + "\n" +
				"required action points : " + g.getCurrentChampion().getAbilities().get(x-1).getRequiredActionPoints() + "\n"						
		));
		ability.setText(g.getCurrentChampion().getAbilities().get(x-1).getName());
	}

	public void Ability(Ability a) {
		if(a.getCastArea() == AreaOfEffect.SELFTARGET||a.getCastArea() == AreaOfEffect.TEAMTARGET||a.getCastArea() == AreaOfEffect.SURROUND) {
			try {
				g.castAbility(a);
			} catch (NotEnoughResourcesException e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage() , ButtonType.CLOSE);
				alert.show();
			} catch (AbilityUseException e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage() , ButtonType.CLOSE);
				alert.show();
			} catch (CloneNotSupportedException e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage() , ButtonType.CLOSE);
				alert.show();
			}
			
		}
		else if(a.getCastArea() == AreaOfEffect.DIRECTIONAL) {
				Stage popup = new Stage();
				
				Label message = new Label("Please Enter a direction");
				
				Button up = new Button("up");
				up.setOnAction(e1 -> {
					Ability(a,Direction.UP);
					popup.close();
				});
				Button down = new Button("DOWN");
				down.setOnAction(e1 -> {
					Ability(a,Direction.DOWN);
					popup.close();
				});
				Button right = new Button("RIGHT");
				right.setOnAction(e1 -> {
					Ability(a,Direction.RIGHT);
					popup.close();
				});
				Button left = new Button("LEFT");
				left.setOnAction(e1 -> {
					Ability(a,Direction.LEFT);
					popup.close();
				});
				
				VBox alert = new VBox();
				alert.getChildren().addAll(message,up ,down,right,left);
				alert.setAlignment(Pos.CENTER);
				Scene tmp = new Scene(alert);
				popup.setScene(tmp);
				popup.show();
				
		}
		else if(a.getCastArea() == AreaOfEffect.SINGLETARGET) {
			Stage popup = new Stage();
			
			Label message = new Label("Please Enter a champion name");
			TextField name = new TextField();
			
			
			Button enter = new Button("Enter");
			//enter.setDefaultButton(true);
			enter.setOnAction(e1 -> {
				if(name.getText() != null) {
					int x = 5;
					int y = 5;
					for(Champion c : g.getFirstPlayer().getTeam()) {
						if(c.getName().equals(name.getText())) {
							x = (int)c.getLocation().getX();
							y = (int)c.getLocation().getY();
						}
					}
					for(Champion c : g.getSecondPlayer().getTeam()) {
						if(c.getName().equals(name.getText())) {
							x = (int)c.getLocation().getX();
							y = (int)c.getLocation().getY();
						}
					}
					try {
					Ability(a, x , y);
					}
					catch(Exception e){
						Alert alert1 = new Alert(AlertType.ERROR, "Please enter a valid name", ButtonType.CLOSE);
						alert1.show();
					}
				}
				else {
					Alert alert = new Alert(AlertType.ERROR, "Please enter a champion", ButtonType.CLOSE);
					alert.show();
				}
				popup.close();
			});
			
			VBox alert = new VBox();
			alert.getChildren().addAll(message,name,enter);
			alert.setAlignment(Pos.CENTER);
			Scene tmp = new Scene(alert);
			popup.setScene(tmp);
			popup.show();
		}
		
		abilityinfo(Ability1, 1);
		abilityinfo(Ability2, 2);
		abilityinfo(Ability3, 3);
	}
	
	public void Ability(Ability a, Direction d) {
		try {
			g.castAbility(a, d);
		} catch (NotEnoughResourcesException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage() , ButtonType.CLOSE);
			alert.show();
		} catch (AbilityUseException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage() , ButtonType.CLOSE);
			alert.show();
		} catch (CloneNotSupportedException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage() , ButtonType.CLOSE);
			alert.show();
		}
		
		abilityinfo(Ability1, 1);
		abilityinfo(Ability2, 2);
		abilityinfo(Ability3, 3);
	}
	
	public void Ability(Ability a , int x, int y) {
		try {
			g.castAbility(a, x, y);
		} catch (NotEnoughResourcesException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage() , ButtonType.CLOSE);
			alert.show();
		} catch (AbilityUseException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage() , ButtonType.CLOSE);
			alert.show();
		} catch (InvalidTargetException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage() , ButtonType.CLOSE);
			alert.show();
		} catch (CloneNotSupportedException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage() , ButtonType.CLOSE);
			alert.show();
		}
		
		abilityinfo(Ability1, 1);
		abilityinfo(Ability2, 2);
		abilityinfo(Ability3, 3);
	}
	
	public void leaderabilityinfo(Button leaderability) {
		String s = "";
		if(g.getCurrentChampion() instanceof Hero)
			s += "hero ability";
		else if(g.getCurrentChampion() instanceof AntiHero)
			s += "antihero ability";
		else if(g.getCurrentChampion() instanceof Villain)
			s += "Villain ability";
				
		leaderability.setTooltip(new Tooltip(s));
	}
	
	public void cleanup(GridPane board, ArrayList<Damageable> gameboard) {
		for(Damageable attacked : gameboard) {
			
			if(attacked.getCurrentHP() == 0) {
				gameboard.remove(attacked);
				ObservableList<Node> children = board.getChildren();
				for(Node node : children) {
						if(GridPane.getRowIndex(node) == 4-(int)attacked.getLocation().getX() && GridPane.getColumnIndex(node) == (int)attacked.getLocation().getY()) {
							board.getChildren().remove(node);
							Button cell = new Button("");
							cell.setMinSize(100, 100);
							board.add(cell , (int)attacked.getLocation().getY() , 4-(int)attacked.getLocation().getX());
							setteams();
							break;
						}
				}
				if(g.checkGameOver() != null) {
					scene5 = startscene5(g.checkGameOver());
					primaryStage.setScene(scene5);
				}
			}
			else { 
				ObservableList<Node> children = board.getChildren();
				for(Node node : children) {
					if(GridPane.getRowIndex(node) == 4-(int)attacked.getLocation().getX() && GridPane.getColumnIndex(node) == (int)attacked.getLocation().getY()) {
						if(attacked instanceof Champion)
							info((Button)node,(Champion)attacked);
						else
							((Button)node).setTooltip(new Tooltip("" + attacked.getCurrentHP()));

						break;

					}
				}				
			}
		}
	}

	public void setteams() {
		firstteam.getChildren().clear();
		secondteam.getChildren().clear();
		for(int i = 0 ;i <= 4 ; i++) {
			for(int j = 0;j <= 4 ; j++) {
				if(g.getBoard()[i][j] instanceof Champion && g.getBoard()[i][j]!=null) {
					if(g.getFirstPlayer().getTeam().contains((Champion)g.getBoard()[i][j])) {
						Button cell = new Button(((Champion)g.getBoard()[i][j]).getName());
						info(cell,(Champion)g.getBoard()[i][j]);
						cell.setStyle("-fx-background-color: #0000FF");
						cell.setMinSize(100, 100);
						if(g.getFirstPlayer().getLeader().equals(g.getBoard()[i][j]))
							cell.setStyle("-fx-background-color: #d8bb78");
						firstteam.getChildren().add(cell);
					}
					else if(g.getSecondPlayer().getTeam().contains((Champion)g.getBoard()[i][j])) {
						Button cell = new Button(((Champion)g.getBoard()[i][j]).getName());
						info(cell,(Champion)g.getBoard()[i][j]);
						cell.setStyle("-fx-background-color: #FF0000");
						cell.setMinSize(100, 100);
						if(g.getSecondPlayer().getLeader().equals(g.getBoard()[i][j]))
							cell.setStyle("-fx-background-color: #1018");
						secondteam.getChildren().add(cell);
					}
				}
			}
		}
	}
	
	public void current() {
		String turn = "turn order : " + "\n" ;
		ArrayList<Champion> turnorder = new ArrayList<Champion>();
		int i = 1;
		while(!g.getTurnOrder().isEmpty()) {
			turnorder.add((Champion) g.getTurnOrder().remove());
		}
		for(Champion c : turnorder) {
			turn += i + "." + c.getName() + "\n";
			g.getTurnOrder().insert(c);
			i++;
			
		}
		current.setText(turn);
	}
	
	public void setcurrent(Label label) {
		Champion c = g.getCurrentChampion();
		String s = "";
		if(c instanceof Hero) {
			s = "Hero \n";
		}
		else if(c instanceof Villain) {
			s = "Villian \n";
		}
		else if(c instanceof AntiHero) {
			s = "AntiHero \n";
		}
		String ap = "applied effects :";
		if(c.getAppliedEffects().isEmpty())
			ap += "None";
		else
			for(model.effects.Effect e : c.getAppliedEffects()) {
				ap += "name : " + e.getName();
				ap += "duration : " + e.getDuration(); 
			}
		String l = "not leader";
		if(g.getFirstPlayer().getLeader() != null &&  g.getFirstPlayer().getLeader().equals(c))
			l = g.getFirstPlayer().getName() + "'s leader";
		else if(g.getFirstPlayer().getLeader() != null && g.getSecondPlayer().getLeader().equals(c))
			l = g.getSecondPlayer().getName() + "'s leader";
		String team = "no team yet";	
		if(g.getFirstPlayer().getTeam().contains(c))
			team = "team : first team";
		else if(g.getSecondPlayer().getTeam().contains(c))
			team = "team : second team";
		
		
		label.setText(s + 
				c.getName() + "\n" +
				team + "\n" +
				 l + "\n" +
				"ability 1 : " + c.getAbilities().get(0).getName() + "\n" +
				"ability 2 : " + c.getAbilities().get(1).getName() + "\n" +
				"ability 3 : " + c.getAbilities().get(2).getName() +  "\n" +
				ap + "\n" +
				"current action points : " + c.getCurrentActionPoints() + "\n" +
				"maximum action point : " + c.getMaxActionPointsPerTurn() +
				"attack damage : " + c.getAttackDamage() + "\n" +
				"attack range : " + c.getAttackRange() + "\n" +
				"HP : " + c.getCurrentHP() + "\n" +
				"mana : " + c.getMana() + "\n" +
				"speed : " + c.getSpeed());
	}
	
	public void leaderabilityused(Label first,Label second) {
		if(g.isFirstLeaderAbilityUsed())
			first.setText("first leader ablility is already used");
		if(g.isSecondLeaderAbilityUsed())
			second.setText("second leader ablility is already used");
		
	}
	
	public Scene startscene5 (Player winner) {
		Label wins = new Label("Congratulations " + winner.getName() + ", you win.");
		Button close = new Button("close");
		close.setOnAction(e -> {
			Platform.exit();
		});
		VBox center = new VBox();
		center.getChildren().addAll(wins,close);
		center.setAlignment(Pos.CENTER);
		BorderPane layout = new BorderPane();
		layout.setCenter(center);
		Scene scene = new Scene(layout, screenSize.getWidth(), screenSize.getHeight());
		return scene;
	}








}












