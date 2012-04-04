import java.util.*;

/**
 * clasa Folder ce extinde Orice folderul contine un vector de ArboreSGF adica de fapt de Orice 
 * @author Merca
 *
 */
public class Folder extends Orice {
	// content pentru folder este de fapt un vector ce contine tot ce contine acel folder
	Collection<ArboreSGF> content = new ArrayList<ArboreSGF>();
	
	Folder(String s){
		super(s);
	}
	/**
	 * 
	 * @param name numele fisierului
	 * @param user_owner numele owner ului fisierului
	 * @param content continutul folderului adica fisierele/folderele pe care le contine
	 * @param type pentru folder este mereu director
	 */
	Folder(String name,User user_owner,Object content,String type) {
		super(name,user_owner,content,type);
		this.type= new String("director");
	
	}
	/**
	 * mkdir creaza un nou folder in ArboreleSGF primit ca parametru
	 * @param user_owner numele ownerul fisierului 
	 * @param path calea catre locul de crearea al fisierului si numele fisierului nou la final
	 * @param arbore arborele in care se va adauga fisierul 
	 * @param content continutul fisierului initial adica null
	 * @param type tipul Orice ului director sau unul din tipurile de fisier
	 * @return Arborele initial modificat
	 */
	public ArboreSGF mkdir(User user_owner,String path,ArboreSGF arbore,Collection<ArboreSGF> content,String type){
		
		return super.createOrice(user_owner, path, arbore, content, type);
	}
	/**
	 * List continutul ultimului director din cale
	 * @param user userul care executa commanda
	 * @param path calea catre folderul pe care trebuie executata comanda ls 
	 * @param arbore arborele SGF pe care se executa ls 
	 */
	public void ls(String user,String path,ArboreSGF arbore) {
		// despartim calea 
		String[] result= path.split("/");
		String str = new String("");
		
		
		// daca se cere sa se listeze continutul lui / caz particular 
		if(path.equals("/")){
		
		
			if((arbore.file.owner_user.user_name.equals(user) && arbore.file.rights.charAt(2) == 'x')
					|| (arbore.file.owner_group !=null&&arbore.file.rights.charAt(5)=='x')
					|| arbore.file.rights.charAt(8) =='x' || user.equals("root")){
				if(arbore.file.owner_group!=null&&!(arbore.file.owner_user.user_name.equals(user) && arbore.file.rights.charAt(2) == 'x')&&!(arbore.file.rights.charAt(8) =='x')&&!(user.equals("root"))){
					Iterator<User> itr = arbore.file.owner_group.users_group.iterator();
					boolean ok1 = false;
					while(itr.hasNext()){
						if(itr.next().user_name.equals(user)){
							ok1= true; break;
						}
					}
					if(!ok1==true|| !(arbore.file.rights.charAt(5) == 'x')){
						System.err.println("Eroare");
					}
				} 
			// iteram prin arborele initial adica prin root si afisam numele folderelor/fisierelor continute
			Iterator<ArboreSGF> it = arbore.noduri.iterator();
		
			while(it.hasNext()){
				ArboreSGF arb = it.next();
				// adaugam continutul lui aux intr un String 
				str	 = str + arb.file.name + " ";
				
			}
			
			}
			else{ System.err.println("Eroare");}
		}
		else{
		ArboreSGF aux = arbore;
		// retinem in last numele ultimului String din path care reprezinta de fapt folderul pe care se va executa ls
		String last = new String(result[result.length-1]);
		// suprascriem primul element din result pentru ca result[0] era mereu ""
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
					
				Iterator<ArboreSGF> it = aux.noduri.iterator();
				boolean ok = false;
				while(it.hasNext()){
					ArboreSGF rg = it.next();
					// daca numele orice ului este acelasi cu al lui t si daca este director  
					if(rg.file.name.equals(t) && rg.file.type.equals("director")){
						aux = rg;
						ok = true;
						break;
					} 
					
				}
				if(ok == false){
						
						System.err.println("Eroare"); break;	
				}
				 	
				 	
				 	
					if((aux.file.owner_user.user_name.equals(user) && aux.file.rights.charAt(2) == 'x')
							|| (aux.file.owner_group !=null&&aux.file.rights.charAt(5)=='x')
							|| aux.file.rights.charAt(8) =='x' || user.equals("root")) { 
						if(aux.file.owner_group!=null&&!(aux.file.owner_user.user_name.equals(user) && aux.file.rights.charAt(2) == 'x')&&!(aux.file.rights.charAt(8) =='x')&&!(user.equals("root"))){
							Iterator<User> itr = aux.file.owner_group.users_group.iterator();
							boolean ok1 = false; 
							while(itr.hasNext()){
								User usr = itr.next();
								if(usr.user_name.equals(user)){
									
									ok1= true; break;
								}
							}
							if(!ok1==true||!(aux.file.rights.charAt(5) == 'x')){
							
								System.err.println("Eroare"); break;
							}
						} 	
						
				
				if(aux.file.type.equals("director")){
					// afisam continul folderul aux adica last
					
					Iterator<ArboreSGF> itr = aux.noduri.iterator();
					while(itr.hasNext()){
						ArboreSGF arb = itr.next();
						// adaugam continutul lui aux intr-un string 
						str = str + arb.file.name + " ";
						
						
					}
				}
				else {
					System.err.println("Eroare");
				}
				
			} else {
				System.err.println("Eroare"); break;
			}
				} 
				

		}//for
		}
		// luam fiecare fisier ce trebuie afisat
		String[] p = str.split(" ");
		// daca nu este gol
		if(!p[0].equals("")){
			//creeam o lista pentru a folosi functia sort din Collections pentru a sorta in ordine lexicografica componentele folderului dat
			List<String> list = Arrays.asList(p);
			Collections.sort(list);
			Iterator<String> iterat = list.iterator();
			while(iterat.hasNext()){
				// afisam fiecare componenta
				System.out.println(iterat.next());
			}
		}
	}// fuc
	/**
	 * rmdir sterge un director apeland functia 
	 * @param user_owner userul care trebuie sa fie owner 
	 * @param path calea catre director
	 * @param arbore
	 * @return arborele modificat
	 */
	public ArboreSGF rmdir(String user_owner,String path,ArboreSGF arbore){
		int ok=1;
		return super.removeOrice(user_owner, path, arbore,ok);
	}
	/**
	 * functie recursiva care parcurge in adancime arborele si pentru fiecare fisier care are grup iar daca grupul este egal cu grupul cautat inlocuim
	 * cu al doilea grup la care este owner user sau null daca nu este owner la alt grup 
	 * @param arbore arbore de fisiere pe care se lucreaza
	 * @param delgroup grupul pe care vrem sa il inlocuim
	 * @param replacegroup grupul cu care vrem sa inlocuim delgroup
	 * @param user userul care executa commanda 
	 * @return arborele modificat
	 */
	public ArboreSGF replaceGroup(ArboreSGF arbore,String delgroup, Group replacegroup, String user){
		// daca arborele este null returnam null
		if(arbore == null){ return null;}
		else
		{	// altfel parcurgem nodurile acestui arbore si luam fiecare arbore in parte parcurgand in adancime arborele pana cand dam de un arbore null
			// adica fara alte noduri sau de un fisier
			Iterator<ArboreSGF> it = arbore.noduri.iterator();
			while(it.hasNext()){
				ArboreSGF arb = it.next();
				// facem modificarile daca are group si daca grupul este egal cu grupul pe care vrem sa il stergem atunci inlocuim
				if(arb.file.owner_group!=null)
				if(arb.file.owner_group.group_name.equals(delgroup)){
					arb.file.owner_group = replacegroup;
					
				}
				// apelam din nou functie de nod al arborelui initial arbore
				replaceGroup(arb, delgroup, replacegroup, user);
			}
		}
		// returnam arborele modificat
		return arbore;
	}
	/**
	 * functie recursiva care parcurge arborele in adancime si sterge din grupul owner al fisierelor userul care a fost sters din grup cu usermod
	 * @param arbore arborele care trebuie modificat
	 * @param group grupul care trebuie sa fie owner
	 * @param deluser userul care trebuie sters din grup 
	 * @return arborele modificat
	 */
public ArboreSGF deleteuserfromgroup(ArboreSGF arbore,String group, String deluser){
	// daca arborele este null returnam null
		if(arbore == null){ return null;}
		else
		{	// altfel parcurgem nodurile acestui arbore si luam fiecare arbore in parte parcurgand in adancime arborele pana cand dam de un arbore null
			// adica fara alte noduri sau de un fisier
			Iterator<ArboreSGF> it = arbore.noduri.iterator();
			while(it.hasNext()){
				ArboreSGF arb = it.next();
				// modificam in grupul owner al fisierului in utilizatorii grupului owner stergem utilizatorul deluser
				if(arb.file.owner_group!=null)
				if(arb.file.owner_group.group_name.equals(group)){
					Iterator<User> itr = arb.file.owner_group.users_group.iterator();
						while(itr.hasNext()){
							User usr = itr.next();
							
							if(usr.user_name.equals(deluser)){
								
								itr.remove(); break;
							}
						}
					
				}
				deleteuserfromgroup(arb, group, deluser);
			}
		}
		return arbore;
	}
}
