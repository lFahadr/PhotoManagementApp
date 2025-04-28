public class PhotoManager {
	  private LinkedList<Photo> photos;

	    public PhotoManager() {
	        photos = new LinkedList<>();
	    }

	    public LinkedList<Photo> getPhotos() {
	        return photos;
	    }

	    public void addPhoto(Photo p) {
	        photos.insert(p);
	    }

	    public void deletePhoto(String path) {
	        LinkedList<Photo>.Node current = photos.iterator();
	        while (current != null) {
	            if (current.data.getPath().equals(path)) {
	                photos.remove(current.data);
	                return;
	            }
	            current = current.next;
	        }
	    }
	}