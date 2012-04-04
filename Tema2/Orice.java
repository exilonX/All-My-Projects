import java.util.*;

/**
 * clasa abstracta ce reprezinta caracteristicile comune ale directoarelor si fisierelor 
 * @author Merca
 *
 */

public abstract class Orice {
	// functie care returneaza toate tipurile de fisiere
	public Collection<String> allTypes(){
		Collection<String> allTypes = new ArrayList<String>();
		allTypes.add("sursa");allTypes.add("html");allTypes.add("mp3");allTypes.add("tex");allTypes.add("zip");
		allTypes.add("txt");allTypes.add("obiect");allTypes.add("executabil");
		return allTypes;
	}
	String name, type;
	Group owner_group;
	User owner_user;
	Object content;
	String rights;
	Orice(String s){
		name = s;
	}
	Orice(String name,User user_owner,Object content,String type){
		this.name = name; 
		this.owner_user= user_owner;
		this.owner_group = owner_user.hasgroup ? owner_user.default_group :null;
		this.content = content;
		this.rights = new String("rwxrwx---");
		
	}
	/**
	 * functie care creeaza orice ori director ori fisier
	 * @param user_owner userul care creeaza fisierul adica owner ul fisierului 
	 * @param path calea catre folderul in care trebuie creat fisierul
	 * @param arbore arborele in care se vor face modificarile
	 * @param content continutul fisierului/ directorului pentru directoare estre null iar pentru fisiere este un String daca se apeleaza cu un string dupa tip
	 * @param type tipul orice ului
	 * @return arborele modificat
	 */
	public ArboreSGF createOrice(User user_owner,String path,ArboreSGF arbore,Object content,String type){

		// un vector de String ce reprezinta nodurile din cale 
		if(path.contains("/")){
		String[] result= path.split("/");
		Orice f;
		String last = new String(result[result.length-1]);
		
		if(type.equals("director")){
			
			f= new Folder(result[result.length-1],user_owner,content,type);
			
		}
		else{
			Collection<String> types = this.allTypes();
			if(types.contains(type)){
				if(type.equals("executabil")|| type.equals("obiect")){
					content = null;
				}
				else
				{	if(!content.equals(""))
				content = ((String)content).substring(1, ((String)content).lastIndexOf("\""));
				}
				
			 f = new File(result[result.length-1],user_owner,content,type);
			}
			else { System.err.println("Eroare"); f=null;}
			
		}
		for(int i1=0;i1<result.length-1;i1++){
			result[i1] = result[i1+1];
		}
		int length = result.length -1;
		
		// arbore auxiliar folosit pentru parcurgere
		ArboreSGF aux = arbore;
		int nr = length;
		int crt =0;
		for(int i=0 ; i< length ; i++){
			if(!result[i].equals("")){
			String t = result[i];
			crt++;
			//daca result nu s-a ajuns la final parcurge arborele si cauta t
			if((t.equals(last) != true || crt !=nr) && t.equals("") == false){
				// iterator folosit pentru a parcurge colectia de noduri 
				Iterator<ArboreSGF> itr = aux.noduri.iterator();
				boolean ok = false;
				// cat timp exista noduri pentru parinte
				while(itr.hasNext()){
					// se retine Arborele 
					ArboreSGF s = (ArboreSGF) itr.next();
					
					// se compara numele arborelui cu primul element din cale in caz de reusita se trece la arborele acela si trecem   
					// la urmatorul nod din cale
					
					if(s.file.name.equals(t) && s.file.type.equals("director")){
						aux = s;
					/*	if(aux.noduri.iterator().hasNext()){
							aux.noduri = new ArrayList<ArboreSGF>();
						}*/
						ok= true;
						break;
					}
					
				}
					// daca nu s-a gasit nodul t printeaza eroare si iesi din for pentru ca path este gresit 
					if(ok==false){
					 	System.err.println("Eroare"); break;
					}
			}
			// if crt == nr   adica ultima chestie numele fisierului adaugam pe aceasta pozitie fisierul nou
			else
			{ 		
				Iterator<ArboreSGF> itr = aux.noduri.iterator();
				boolean ok1 = true;
					while(itr.hasNext()){
						if(itr.next().file.name.equals(last)){
							ok1=false;
							break;
						}
					}
					
			if(ok1==true){		
				// verificam drepturile asupra folderului parinte al Orice ului ce va fi creat  
				if(user_owner.user_name.equals("root") || (aux.file.owner_user.user_name.equals(user_owner.user_name) && aux.file.rights.charAt(1) == 'w')
					|| (aux.file.owner_group !=null&&aux.file.rights.charAt(4)=='w' )
					|| aux.file.rights.charAt(7) =='w'){
					
					if(aux.file.owner_group!=null&&!(aux.file.owner_user.user_name.equals(user_owner.user_name) && aux.file.rights.charAt(1) == 'w')&&!(aux.file.rights.charAt(7) =='w')&&!(user_owner.user_name.equals("root"))){
						Iterator<User> iter = aux.file.owner_group.users_group.iterator();
						boolean ok2 = false;
						while(iter.hasNext()){
							if(iter.next().user_name.equals(user_owner)){
								ok2= true; break;
							}
						}
						if(!ok2==true||!(aux.file.rights.charAt(4) == 'w')){
							continue;
						}
						else {
							System.err.println("Eroare");
						}
					} 
						ArboreSGF arb = new ArboreSGF();
						arb.file = f;
						arb.noduri = new ArrayList<ArboreSGF>();
						
						if(f.type.equals("director")){
							((Folder)aux.file).content.add(arb);
						}
						aux.noduri.add(arb);
					}
					else{
						System.err.println("Eroare");
					}
			} else {System.err.println("Eroare");}
			}
			}//if
		} // for 
		}else	{ System.err.println("Eroare");}
		return arbore;
		
}
	/**
	 * functie care este apelata atat in rmdir cat si in delete pentru a sterge ori un fisier ori un director 
	 * @param user_owner userul care a executat comanda
	 * @param path calea catre fisierul/directorul care trebuie sters 
	 * @param arbore arborele in care trebuie facute modificarile
	 * @param oktip tipul 0 pentru fisier si 1 pentru director
	 * @return arborele modificat
	 */
public ArboreSGF removeOrice(String user_owner,String path,ArboreSGF arbore,int oktip){
		
	String[] result= path.split("/");
	
	ArboreSGF aux = arbore;
	String last = new String(result[result.length-1]);

	for(int i1=0;i1<result.length-1;i1++){
		result[i1] = result[i1+1];
	}
	int length = result.length -1;
	int nr = length;
	int crt =0;
	
	for(int i=0;i<length;i++){
		String t = result[i];
		crt++;
		//daca result nu s-a ajuns la final parcurge arborele si cauta t
		if((t.equals(last) != true || crt !=nr) && t.equals("") == false){
			// iterator folosit pentru a parcurge colectia de noduri 
			
			Iterator<ArboreSGF> itr = aux.noduri.iterator();
			boolean ok = false;
			// cat timp exista noduri pentru parinte
			while(itr.hasNext()){
				// se retine Arborele 
				ArboreSGF s = (ArboreSGF) itr.next();
				// se compara numele arborelui cu primul element din cale in caz de reusita se trece la arborele acela si trecem   
				// la urmatorul nod din cale
				if(s.file.name.equals(t) && s.file.type.equals("director")){
					
					aux = s;
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
				
				if((aux.file.owner_user.user_name.equals(user_owner) && aux.file.rights.charAt(1) == 'w')
								|| (aux.file.owner_group !=null&&aux.file.rights.charAt(4)=='w')
								|| aux.file.rights.charAt(7) =='w' || user_owner.equals("root")){
					// && aux.file.owner_group.users_group.contains(usr)== true && aux.file.rights.charAt(4) == 'w'
					if(aux.file.owner_group!=null&&!(aux.file.owner_user.user_name.equals(user_owner) && aux.file.rights.charAt(1) == 'w')&&!(aux.file.rights.charAt(7) =='w')&&!(user_owner.equals("root"))){
						Iterator<User> itr = aux.file.owner_group.users_group.iterator();
						boolean ok1 = false;
						while(itr.hasNext()){
							if(itr.next().user_name.equals(user_owner)){
								ok1= true; break;
							}
						}
						if(!ok1==true||!(aux.file.rights.charAt(4) == 'w')){
							
							System.err.println("Eroare"); break;
						}
					}
					
					if(aux.noduri.isEmpty()!= true){
						Iterator<ArboreSGF> it = aux.noduri.iterator();
						boolean ok = false;
						while(it.hasNext()){
							ArboreSGF auxit = it.next();
							if(auxit.file.name.equals(last)){
									// verificam daca user ul are drepturi 
						//		if(auxit.file.owner_group!=null)
								
									if((auxit.file.owner_user.user_name.equals(user_owner) && auxit.file.rights.charAt(1) == 'w')
											|| (auxit.file.owner_group !=null&&auxit.file.rights.charAt(4)=='w')
											|| auxit.file.rights.charAt(7) =='w' || user_owner.equals("root")){
										
										if(aux.file.owner_group!=null&&!(aux.file.owner_user.user_name.equals(user_owner) && aux.file.rights.charAt(1) == 'w')&&!(aux.file.rights.charAt(7) =='w')&&!(user_owner.equals("root"))){
											Iterator<User> itr = aux.file.owner_group.users_group.iterator();
											boolean ok1 = false;
											while(itr.hasNext()){
												if(itr.next().user_name.equals(user_owner)){
													ok1= true; break;
												}
											}
											if(!ok1==true||!(aux.file.rights.charAt(4) == 'w')){
											
												System.err.println("Eroare"); break;
											}
										} 
										if((oktip ==1 && auxit.file.type.equals("director")) || (oktip ==0 && !auxit.file.type.equals("director")) ){
											it.remove();
											ok = true;
											break;
										}
									}

								
							}
						}
						if(ok == false){
							System.err.println("Eroare");
						}
						
					} else {System.err.println("Eroare");
						} 
		
				}// if 
				else { System.err.println("Eroare");}
			}//else
			
		
	}//for
	
		return arbore;
	}

	
	
	
}
