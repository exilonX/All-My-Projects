import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;



/**
 * functia main
 * @author merca
 *
 */
public class Main {
	
	public Collection<String> allTypes(){
		Collection<String> allTypes = new ArrayList<String>();
		allTypes.add("sursa");allTypes.add("html");allTypes.add("mp3");allTypes.add("tex");allTypes.add("zip");
		allTypes.add("txt");allTypes.add("obiect");allTypes.add("executabil");
		return allTypes;
	}
	/**
	 * @param args
	 * functia main in care se fac prelucrarile comenzilor si apelarea functiilor corespunzatoare
	 */
	public static void main(String[] args) {
		// for care se opreste doar cand se citeste quit 
		Collection<String> allTypes = new ArrayList<String>();
		allTypes.add("sursa");allTypes.add("html");allTypes.add("mp3");allTypes.add("tex");allTypes.add("zip");
		allTypes.add("txt");allTypes.add("obiect");allTypes.add("executabil");
	
		
		ArboreSGF sgf = new ArboreSGF(); 
		sgf.file.rights = "rwxrwxrwx";
		// retinem numele userului si un arrayList de grupuri la care este owner default 
		Map<String,ArrayList<Group>> protect = new TreeMap<String, ArrayList<Group>>();
		// o mapa in care retine numele grupului si grupul propriu zis 
		Map<String,Group> protect1 = new TreeMap<String, Group>();
		Scanner s = new Scanner(System.in);
		for(;;){
			// citim comanda si incepem sa o prelucram
			
			String command = s.nextLine();
			
			// break pentru quit
			if(command.equals("quit") == true){
				break;
			}
			
			else{ 
				//prelucram userul de la inceputul comenzii pana la primult " " 
				String user = new String();
				user = command.substring(0, command.indexOf(' '));
				
				String task = new String();
				if(command.substring(user.length()+1).contains(" "))
				task= command.substring(command.indexOf(' ')+1,1+command.indexOf(' ')+command.substring(command.indexOf(' ')+1).indexOf(' '));
				else task = command.substring(user.length()+1);
				// verificam tipul task ului si efectuam pentru o comanda o anumita prelucrare a comenzii 
				
				if( task.equals("create") || task.equals("write")){

			command = command.substring(2+command.indexOf(' ')+command.substring(command.indexOf(' ')+1).indexOf(' '));
					String path = new String();
					// verificam corectitudinea comenzii 
					if(command.contains(" ")){
					path = command.substring(0, command.indexOf(" "));
					}
					else { System.err.println("Eroare"); continue; }
					
					if(task.equals("create")){
						command = command.substring(command.indexOf(" ")+1);
						
						if(command.contains(" ") && !command.substring(command.indexOf(" ")+1).equals("")){
							String type = command.substring(0,command.indexOf(" "));
							// verificam daca tipul fisierului este un tip bun 
							if(allTypes.contains(type)){
								command = command.substring(command.indexOf(" ")+1);
								String content = command;
								File f = new File("");
								//adaugam in sgf noul nod creat in functia create
								Group grp;
								// verificam daca userul are un grup default 
								if(protect.get(user)!=null)
									grp = protect.get(user).iterator().next();
								else
									grp =null;
									// creeam fisierul cu user owner user si group owner grupul default al userului sau null daca nu are grup
									User usr = new User(user, grp);
									sgf = f.create(usr, path, sgf, content, type);
									
							}
							else {System.err.println("Eroare");}
						} else	{
							String type;
							
									if(command.contains(" "))
									type = command.substring(0,command.indexOf(" "));
									else type = command.substring(0);
									
								// verificam daca 
								if(allTypes.contains(type)){
								String content = new String("");
								File f = new File("");
								Group grp;
								if(protect.get(user)!=null)
									grp = protect.get(user).iterator().next();
								else
									grp =null;
								User usr = new User(user, grp);
								
								sgf = f.create(usr, path, sgf, content, type);
							} else System.err.println("Eroare");
								}
						
					} 
					if(task.equals("write")){
						
						command = command.substring(command.indexOf(" ")+1);
						String newContent = command.substring(1,command.lastIndexOf("\""));
						File f = new File("");
						// apelam functia write de content nou si calea catre fisier 
						sgf= f.write(user, path, sgf, newContent);
					}
				}
				else if(task.equals("mkdir")|| task.equals("read")||task.equals("delete")||task.equals("rmdir")
						|| task.equals("execute")||task.equals("ls")){

					command = command.substring(2+command.indexOf(' ')+command.substring(command.indexOf(' ')+1).indexOf(' '));
				
					String path = new String();
					path = command.substring(0);
					if(task.equals("mkdir")){
						Folder f = new Folder("");
						Group grp;
						if(protect.get(user)!=null)
							grp = protect.get(user).iterator().next();
						else
							grp =null;
						User usr = new User(user , grp);
						sgf = f.createOrice(usr, path, sgf, null, "director");
					
					}
					if(task.equals("ls")){
						Folder f = new Folder("");
						f.ls(user, path, sgf);
					}
					if(task.equals("read")){
						File f = new File("");
						f.read(user, path, sgf);
					}
					if(task.equals("execute")){
						File f = new File("");
						f.execute(user, path, sgf);
					}
					if(task.equals("delete")){
						File f = new File("");
						f.delete(user, path, sgf);
					}
					if(task.equals("rmdir")){
						Folder f = new Folder("");
						f.rmdir(user, path, sgf);
					}
					
				} else
				if(task.equals("groupadd")||task.equals("groupdel")||task.equals("useradd")||task.equals("usermod")||task.equals("chmod")){
					
					if(task.equals("groupadd")){
						String newgroup = command.substring(command.lastIndexOf(" ")+1);
						// daca nu exista grupul newgroup pentru a nu exista duplicate
						if(!protect1.containsKey(newgroup)){
						//daca userul nu mai are nici un altgroup
							if(protect.get(user)==null){
							// creeam userul  default si grupul nou 
							User usr = new User(user, null);
							
							Group g = new Group(newgroup, usr);
							//facem grupul default al userului primul grup creat de acesta 
							usr.default_group = g;
							usr.hasgroup = true;
							// adaugam la grupurile userului grupul nou
							usr.user_groups.add(g);
							// facem owner pe usr in grupul newgroup
							g.group_owner=usr;
							// si adaugam la grup pe usr 
							g.users_group.add(usr);
							// creeam un arraylist care o sa contine grupurile pe care le detine user
							Collection<Group> colgrp = new ArrayList<Group>();
							// adaugam grupul
							colgrp.add(g);
							// adaugam la mapa de useri si grupurile la care este owner 
							protect.put(user, (ArrayList<Group>) colgrp);
							// adaugam la map de grupuri numele grupului si grupul nou creat
							protect1.put(newgroup, g);
							}
						 else{
							 //daca userul are deja un grup default gasim Arraylist ul de grupuri si luam userul din primul group default
							Iterator itre = protect.get(user).iterator();
							User usr = ((Group)itre.next()).group_owner;
							// creeam noul grup cu owner usr gasit si cu numele newgroup
							Group g = new Group(newgroup, usr);
							// adaugam la mapa de useri si grupurile lor la userul user ii adaugam in arraylist ul de grupuri grupul nou creat
							protect.get(user).add(g);
							//adaugam noul arrayList 
							protect.put(user, protect.get(user));
							// adaugam noul grup
							protect1.put(newgroup, g);
						 	}
						}
						else
						{// daca exista grupul eroare
						System.err.println("Eroare");
						}
						
					}
					if(task.equals("groupdel")){
						Group toreplace = null;
						
						String delgroup = command.substring(command.lastIndexOf(" ")+1);
						if(protect1.containsKey(delgroup)){
							// daca exista grupul
							if(protect1.get(delgroup).group_owner.user_name.equals(user)){
								//daca ownerul grupul este user
								// iteram in grupurile userului si gasim al doilea grup creat ca owner daca nu se gaseste nimic toreplace = null
								Iterator itr = protect.get(user).iterator();
								boolean ok=false;
								while(itr.hasNext()){
									Group grp = ((Group)itr.next());
									if(!(grp.group_name.equals(delgroup))){
										toreplace = grp;
										ok =true;
									}
								}
								if(ok == true){
									// daca s-a gasit grup de inlocuit
									Folder f= new Folder("");
									// apelam functia replaceGroup care parcurge tot arborele si inlocuieste grupul owner al fisierele care aveau
									// ca owner group grupul de sters cu grupul gasit mai devreme
									sgf =f.replaceGroup(sgf, delgroup, toreplace, user);
									//stergem grupul
									protect.get(protect1.get(delgroup).group_owner.user_name).remove(0);
									protect1.remove(delgroup);
									
									
								} else {	// daca nu se gaseste un alt group in afara de delgroup
									Folder f= new Folder("");	
									// se inlocuieste grupul delgroup cu null
									sgf = f.replaceGroup(sgf, delgroup, null, user);
								}
								
							}
							else if(user.equals("root")){
								// la fel si pentru root
								Iterator itr = protect.get(protect1.get(delgroup).group_owner.user_name).iterator();
								boolean ok=false;
								while(itr.hasNext()){
									Group grp = ((Group)itr.next());
									if(!(grp.group_name.equals(delgroup))){
										toreplace = grp;
										ok =true;
									}
								}
								if(ok == true){
									
									Folder f= new Folder("");
									sgf =f.replaceGroup(sgf, delgroup, toreplace, user);
									
									
								} else {	// daca nu se gaseste un alt group in afara de delgroup
									Folder f= new Folder("");	
									sgf = f.replaceGroup(sgf, delgroup, null, user);
									
								}
								
							}
							else {System.err.println("Eroare");}
							
							
							
						}
						else { System.err.println("Eroare");}
							
					}// groupdel
					if(task.equals("useradd")){
						
						command = command.substring(command.indexOf(" ")+9);
						if(command.contains(" ")){
						String grptoaddto = command.substring(0,command.indexOf(" "));
						
						
						String usertoadd = command.substring(command.indexOf(" ")+1);
						//daca exista grupul la care vrem sa adaugam si daca userul este root sau ownerul grupului
						if(protect1.containsKey(grptoaddto)&&(user.equals("root")|| user.equals(protect1.get(grptoaddto).group_owner.user_name))  ){
							//creeam userul de adaugat cu numele usertoadd si grupul cu numele grptoaddto
							User usrtoadd = new User(usertoadd,protect1.get(grptoaddto));
							// adaugam in arraylist ul de useri ai grupului userul nou
							protect1.get(grptoaddto).users_group.add(usrtoadd);
							// adaugam si in cea de-a doua mapa la userul default al grupului grouptoaddto in userii grupului noul user
							Iterator<Group> itr = protect.get(protect1.get(grptoaddto).group_owner.user_name).iterator();
							while(itr.hasNext()){
								Group g = (Group) itr.next();
								if(g.group_name.equals(grptoaddto)){
									g.users_group.add(usrtoadd);
								}
								
							}
							
						}
						else {
							System.err.println("Eroare");
						}
					} else { System.err.println("Eroare"); continue;}
					}
					if(task.equals("usermod")){
						command =command.substring(command.indexOf(" ")+9);
						
						String usertodel = command.substring(command.lastIndexOf(" ")+1);
						String groupstodelfrom = command.substring(0,command.length()-usertodel.length()-1);
						String[] grptodel = groupstodelfrom.split(" ");
						//daca userul nu incearca sa se stearga singur
						if(!usertodel.equals(user)){
							// parcurgem toate grupurile primite ca parametru 
							for(int i=0;i<grptodel.length;i++){
								// daca exista grupul si daca ownerul grupului este user sau daca este user este root
								if(protect1.containsKey(grptodel[i]) && (protect1.get(grptodel[i]).group_owner.user_name.equals(user) || user.equals("root"))){
									// iteram prin userii grupului
									Iterator<User> itr = protect1.get(grptodel[i]).users_group.iterator();
									boolean ok = false;
									while(itr.hasNext()){
										User usr = itr.next();
										// daca gasim userul care trebuie sters il stergem
										if(usr.user_name.equals(usertodel)){
											itr.remove();
											ok = true;
											break;
										}
										
									}
									Folder g = new Folder("");
									// stergem in toate fisierele din grupul owner acest utilizator 
									sgf =g.deleteuserfromgroup(sgf, grptodel[i], usertodel);
									if(ok==false){ System.err.println("Eroare");}
								}
							}
						} else {System.err.println("Eroare");}
						
					}
					if(task.equals("chmod")){
						command = command.substring(user.length()+7);
						
						String atwho = command.substring(0,command.indexOf(" "));
						command = command.substring(command.indexOf(" ")+1);
						
						String what = command.substring(0,command.indexOf(" "));
						command = command.substring(command.indexOf(" ")+1);
						if(command.contains(" ")){
						String  tomodify = command.substring(0,command.indexOf(" "));
						command = command.substring(command.indexOf(" ")+1);
						
						String path = command;
						String[] result= path.split("/");
						for(int i1=0;i1<result.length-1;i1++){
							result[i1] = result[i1+1];
						}
						int length = result.length -1;
						String last = result[length];
						ArboreSGF aux = sgf;
						int crt =0;
						for(int i=0;i<length;i++){
							String t = result[i];
							crt++;
							//daca result nu s-a ajuns la final parcurge arborele si cauta t
							if((t.equals(last) != true || crt !=length) && t.equals("") == false){
								// iterator folosit pentru a parcurge colectia de noduri 
								
								Iterator<ArboreSGF> itr = aux.noduri.iterator();
								boolean ok = false;
								// cat timp exista noduri pentru parinte
								while(itr.hasNext()){
									// se retine Arborele 
									ArboreSGF arb = (ArboreSGF) itr.next();
									// se compara numele arborelui cu primul element din cale in caz de reusita se trece la arborele acela si trecem   
									// la urmatorul nod din cale
									if(arb.file.name.equals(t) && arb.file.type.equals("director")){
										
										aux = arb;
										ok=true;
										break;
									}
									
								}
								// daca nu s-a gasit nodul t printeaza eroare si iesi din for pentru ca path este gresit 
								if(ok==false){
								 	System.err.println("Eroare"); break;
								}

							}//if t.eq
							else{ 		
										if(aux.noduri.isEmpty()!= true){
											Iterator<ArboreSGF> it = aux.noduri.iterator();
											boolean treci = false;
											while(it.hasNext()){
												ArboreSGF auxit = it.next();
												
												if(auxit.file.name.equals(last)){ treci=true;
														// verificam daca user ul are drepturi 
											//		if(auxit.file.owner_group!=null)
													// daca user are drepturi de write sau daca este in grup si are drepturi de w sau daca all are drepturi de w sau daca este root
														if((auxit.file.owner_user.user_name.equals(user) && auxit.file.rights.charAt(1) == 'w')
																|| (auxit.file.owner_group !=null&&auxit.file.rights.charAt(4)=='w')
																|| auxit.file.rights.charAt(7) =='w' || user.equals("root")){
															// cautam daca este in grupul owner userul
															if(aux.file.owner_group!=null){
																Iterator<User> itr = aux.file.owner_group.users_group.iterator();
																boolean ok1 = false;
																while(itr.hasNext()){
																	if(itr.next().user_name.equals(user)){
																		ok1= true; break;
																	}
																}
																if(!ok1==true||!(auxit.file.rights.charAt(4) == 'w')){
																
																	System.err.println("Eroare");
																}
															} 
															// se fac prelucrarile 
															// am folosit un string buffer pentru a putea sa modific un caracter pe o anumita pozitie dintr-un string 
															// la final am convertit pe strbuf la string
															StringBuffer strbuf = new StringBuffer(auxit.file.rights);
															// am pus conditii pentru fiecare caz in parte si am modicat Stringul rights al fisierului pentru care am vrut sa modificam comanda
															if(atwho.equals("u")){
																if(what.equals("+")){
																	if(tomodify.length()<=3){
																		for(int k=0;k<tomodify.length();k++){
																			
																			if(tomodify.charAt(k) == 'r'){
																				strbuf.setCharAt(0, 'r');
																			
																			}else if(tomodify.charAt(k) == 'w'){
																				strbuf.setCharAt(1, 'w');
																			}
																			else if(tomodify.charAt(k) == 'x'){
																				strbuf.setCharAt(2, 'x');
																			}else { System.err
																					.println("Eroare");}
																			
																		}
																		
																	} else { System.err
																			.println("Eroare");}
																	
																}
																else if(what.equals("=")){
																	if(tomodify.length()<=3){
																		for(int k=0;k<tomodify.length();k++){
																			
																			if(tomodify.charAt(k) == 'r'){
																				strbuf.setCharAt(0, 'r');
																			
																			}else if(tomodify.charAt(k) == 'w'){
																				strbuf.setCharAt(1, 'w');
																			}
																			else if(tomodify.charAt(k) == 'x'){
																				strbuf.setCharAt(2, 'x');
																			}else { System.err
																					.println("Eroare");}
																			
																		}
																		if(!tomodify.contains("r")){
																			strbuf.setCharAt(0, '-');
																		}if(!tomodify.contains("w")){
																			strbuf.setCharAt(1, '-');
																		}if(!tomodify.contains("x")){
																			strbuf.setCharAt(2, '-');
																		}
																		
																		
																	} else { System.err
																			.println("Eroare");}
																	
																}else if(what.equals("-")){
																	if(tomodify.length()<=3){
																		for(int k=0;k<tomodify.length();k++){
																			
																			if(tomodify.charAt(k) == 'r'){
																				strbuf.setCharAt(0, '-');
																			
																			}else if(tomodify.charAt(k) == 'w'){
																				strbuf.setCharAt(1, '-');
																			}
																			else if(tomodify.charAt(k) == 'x'){
																				strbuf.setCharAt(2, '-');
																			}else { System.err
																					.println("Eroare");}
																			
																		}																		
																	} else { System.err
																			.println("Eroare");}
																	
																} else { System.err
																		.println("Eroare");}
															}
															else if(atwho.equals("g")){
																if(what.equals("+")){
																	if(tomodify.length()<=3){
																		for(int k=0;k<tomodify.length();k++){
																			
																			if(tomodify.charAt(k) == 'r'){
																				strbuf.setCharAt(3, 'r');
																			
																			}else if(tomodify.charAt(k) == 'w'){
																				strbuf.setCharAt(4, 'w');
																			}
																			else if(tomodify.charAt(k) == 'x'){
																				strbuf.setCharAt(5, 'x');
																			}else { System.err
																					.println("Eroare");}
																			
																		}
																		
																	} else { System.err
																			.println("Eroare");}
																	
																}
																else if(what.equals("=")){
																	if(tomodify.length()<=3){
																		for(int k=0;k<tomodify.length();k++){
																			
																			if(tomodify.charAt(k) == 'r'){
																				strbuf.setCharAt(3, 'r');
																			
																			}else if(tomodify.charAt(k) == 'w'){
																				strbuf.setCharAt(4, 'w');
																			}
																			else if(tomodify.charAt(k) == 'x'){
																				strbuf.setCharAt(5, 'x');
																			}else { System.err
																					.println("Eroare");}
																			
																		}
																		if(!tomodify.contains("r")){
																			strbuf.setCharAt(3, '-');
																		}if(!tomodify.contains("w")){
																			strbuf.setCharAt(4, '-');
																		}if(!tomodify.contains("x")){
																			strbuf.setCharAt(5, '-');
																		}
																		
																		
																	} else { System.err
																			.println("Eroare");}
																	
																}else if(what.equals("-")){
																	if(tomodify.length()<=3){
																		for(int k=0;k<tomodify.length();k++){
																			
																			if(tomodify.charAt(k) == 'r'){
																				strbuf.setCharAt(3, '-');
																			
																			}else if(tomodify.charAt(k) == 'w'){
																				strbuf.setCharAt(4, '-');
																			}
																			else if(tomodify.charAt(k) == 'x'){
																				strbuf.setCharAt(5, '-');
																			}else { System.err
																					.println("Eroare");}
																			
																		}																		
																	} else { System.err
																			.println("Eroare");}
																	
																} else { System.err
																		.println("Eroare");}
															
															}
															else if(atwho.equals("a")){
																if(what.equals("+")){
																	if(tomodify.length()<=3){
																		for(int k=0;k<tomodify.length();k++){
																			
																			if(tomodify.charAt(k) == 'r'){
																				strbuf.setCharAt(6, 'r');
																			
																			}else if(tomodify.charAt(k) == 'w'){
																				strbuf.setCharAt(7, 'w');
																			}
																			else if(tomodify.charAt(k) == 'x'){
																				strbuf.setCharAt(8, 'x');
																			}else { System.err
																					.println("Eroare");}
																			
																		}
																		
																	} else { System.err
																			.println("Eroare");}
																	
																}
																else if(what.equals("=")){
																	if(tomodify.length()<=3){
																		for(int k=0;k<tomodify.length();k++){
																			
																			if(tomodify.charAt(k) == 'r'){
																				strbuf.setCharAt(6, 'r');
																			
																			}else if(tomodify.charAt(k) == 'w'){
																				strbuf.setCharAt(7, 'w');
																			}
																			else if(tomodify.charAt(k) == 'x'){
																				strbuf.setCharAt(8, 'x');
																			}else { System.err
																					.println("Eroare");}
																			
																		}
																		if(!tomodify.contains("r")){
																			strbuf.setCharAt(6, '-');
																		}if(!tomodify.contains("w")){
																			strbuf.setCharAt(7, '-');
																		}if(!tomodify.contains("x")){
																			strbuf.setCharAt(8, '-');
																		}
																		
																		
																	} else { System.err
																			.println("Eroare");}
																	
																}else if(what.equals("-")){
																	if(tomodify.length()<=3){
																		for(int k=0;k<tomodify.length();k++){
																			
																			if(tomodify.charAt(k) == 'r'){
																				strbuf.setCharAt(6, '-');
																			
																			}else if(tomodify.charAt(k) == 'w'){
																				strbuf.setCharAt(7, '-');
																			}
																			else if(tomodify.charAt(k) == 'x'){
																				strbuf.setCharAt(8, '-');
																			}else { System.err
																					.println("Eroare");}
																			
																		}																		
																	} else { System.err
																			.println("Eroare");}
																	
																} else { System.err
																		.println("Eroare");}
															
																
															} else { System.err
																	.println("Eroare");
																}
															auxit.file.rights = strbuf.toString();
															
														}else { System.err
																.println("Eroare"); break;} 

													
												} 
												
											}
											if(treci == false){ System.err
												.println("Eroare");}
											
										} else {System.err.println("Eroare");
											} 
							
								
								}//else
								
							
						}
						
						
						
						}
						else { System.err.println("Eroare");}
					}
					
				}
				else { System.err.println("Eroare");}	
			}// else quit
				
		}//for 

	}// main

}

