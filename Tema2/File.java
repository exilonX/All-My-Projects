import java.util.Iterator;


public class File extends Orice {
	String content;
	
	File(String s){
		super(s);
	}
	File(String name,User user_owner,Object content,String type) {
		super(name,user_owner,content,type);
		this.type = type;
	
		
	}
	

	/**
	 * 
	 * @param user_owner owner ul fisierului care va fi creat adica ownerul cu care s-a rulat comanda
	 * @param path calea spre locul de creare al fisierului
	 * @param arbore 
	 * @param content
	 * @param type
	 * @return
	 */
	public ArboreSGF create(User user_owner,String path,ArboreSGF arbore,String content,String type){
		return super.createOrice(user_owner, path, arbore, content, type);
	}
	/**
	 * citeste un fisier daca are un tip bun adica nu este executabil
	 * @param user userul pentru care trebuie sa verificam permisiunile
	 * @param path calea catre fisier
	 * @param arbore arborele in care trebuie sa cautam
	 */
	public void  read(String user,String path,ArboreSGF arbore){
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
					if(aux.noduri.isEmpty()!= true){
					Iterator<ArboreSGF> it = aux.noduri.iterator();
					boolean ok = false;
					while(it.hasNext()){
						ArboreSGF auxit = it.next();
						if(auxit.file.name.equals(last)){
							if(!auxit.file.type.equals("director")  && !auxit.file.type.equals("executabil")){
								// verificam daca user ul are drept de citire pentru fisier sau daca fisierul are drept de citire pentru all
								if((auxit.file.owner_user.user_name.equals(user) && auxit.file.rights.charAt(0) == 'r')
									|| (auxit.file.owner_group !=null &&auxit.file.rights.charAt(3)=='r')
									|| auxit.file.rights.charAt(6) =='r' || user.equals("root")){
									if(auxit.file.owner_group!=null&&!(auxit.file.owner_user.user_name.equals(user) && auxit.file.rights.charAt(0) == 'r')&&!(auxit.file.rights.charAt(6) =='r')&&!(user.equals("root"))){
										Iterator<User> itr = auxit.file.owner_group.users_group.iterator();
										boolean ok1 = false;
										while(itr.hasNext()){
											
											if(itr.next().user_name.equals(user)){
												ok1= true; break;
											}
										}
										if(!ok1==true|| !(auxit.file.rights.charAt(3) == 'r')){
										
											System.err.println("Eroare"); continue;
										}
									} 
									System.out.println(auxit.file.type + " " + (String)(auxit.file.content));
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
			}//else
				
			
		}//for
		
	}
	/**
	 * functie care modifica contentul unui fisier adaugand newcontent
	 * @param user userul pentru care trebuie verificate permisiunile
	 * @param path calea catre fisier
	 * @param arbore arborele in care cautam fisierul
	 * @param newcontent noul content
	 * @return arborele modificat
	 */
	public ArboreSGF write(String user,String path,ArboreSGF arbore,String newcontent){
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
				// iterator folosit pentru a parcurge colectia de noduri din ArboseSGF adica directoarele si fisierele continute in acel director
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
					if(aux.noduri.isEmpty()!= true){
					Iterator<ArboreSGF> it = aux.noduri.iterator();
					boolean ok = false;
					while(it.hasNext()){
						ArboreSGF auxit = it.next();
						if(auxit.file.name.equals(last)){
							if(!auxit.file.type.equals("director")  && !auxit.file.type.equals("obiect") && !auxit.file.type.equals("executabil")){
								// verificam daca user ul are drept de citire pentru fisier sau daca fisierul are drept de citire pentru all
								if((auxit.file.owner_user.user_name.equals(user) && auxit.file.rights.charAt(1) == 'w')
									|| (auxit.file.owner_group !=null&&auxit.file.rights.charAt(4)=='w')
									|| auxit.file.rights.charAt(7) =='w' || user.equals("root")){
									if(auxit.file.owner_group!=null&&!(auxit.file.owner_user.user_name.equals(user) && auxit.file.rights.charAt(1) == 'w')&&!(auxit.file.rights.charAt(7) =='w')&&!(user.equals("root"))){
										Iterator<User> itr = auxit.file.owner_group.users_group.iterator();
										boolean ok1 = false;
										while(itr.hasNext()){
											if(itr.next().user_name.equals(user)){
												ok1= true; break;
											}
										}
										if(!ok1==true||!(auxit.file.rights.charAt(4) == 'w')){
											continue;
										}
										else {
											System.err.println("Eroare");
										}
									} 
										auxit.file.content = newcontent;
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
			}//else
				
			
		}//for
		return arbore;
	}
	/**
	 * executa un fisier executabil adica afiseaza Fisierul a fost executat cu succes
	 * @param user userul care executa commanda 
	 * @param path calea catre fisierul executabil
	 * @param arbore arborele in care cautam fisierul executabil
	 */
	public void  execute(String user,String path,ArboreSGF arbore){
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
					if(aux.noduri.isEmpty()!= true){
					Iterator<ArboreSGF> it = aux.noduri.iterator();
					boolean ok = false;
					while(it.hasNext()){
						ArboreSGF auxit = it.next();
						if(auxit.file.name.equals(last)){
							if(auxit.file.type.equals("executabil")){
								// verificam daca user ul are drept de citire pentru fisier sau daca fisierul are drept de citire pentru all
								if((auxit.file.owner_user.user_name.equals(user) && auxit.file.rights.charAt(2) == 'x')
									|| (auxit.file.owner_group !=null&&auxit.file.rights.charAt(5)=='x')
									|| auxit.file.rights.charAt(8) =='x' || user.equals("root")){
									
									if(auxit.file.owner_group!=null&&!(auxit.file.owner_user.user_name.equals(user) && auxit.file.rights.charAt(2) == 'x')&&!(auxit.file.rights.charAt(8) =='x')&&!(user.equals("root"))){
										Iterator<User> itr = auxit.file.owner_group.users_group.iterator();
										boolean ok1 = false;
										while(itr.hasNext()){
											if(itr.next().user_name.equals(user)){
												ok1= true; break;
											}
										}
										if(!ok1==true||!(auxit.file.rights.charAt(5) == 'x')){
										
											System.err.println("Eroare"); continue;
										}
									} 
									System.out.println("Fisierul a fost executat cu succes");
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
			}//else
				
			
		}//for
		
	}
	/**
	 * sterge un fisier dintr-un director 
	 * @param user_owner userul pentru care trebuie verificate permisiunile
	 * @param path calea catre fisier
	 * @param arbore 
	 * @return arborele modificat
	 */
	public ArboreSGF delete(String user_owner,String path,ArboreSGF arbore){
		int ok=0;
		return super.removeOrice(user_owner, path, arbore,ok);
	}
}
	

