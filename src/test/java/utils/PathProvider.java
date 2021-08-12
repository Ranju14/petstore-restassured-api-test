package utils;

public class PathProvider {
	
	public static String provideFindPetByIdPath(int id){
        String path = "/pet/"+id;
        System.out.println(path);
        return path;
    }
}
