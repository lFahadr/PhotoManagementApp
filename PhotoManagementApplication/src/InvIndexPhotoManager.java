import java.util.Scanner;
public class InvIndexPhotoManager {
    private LinkedList<Photo> photos;
    private BST<LinkedList<Photo>> index;

    public InvIndexPhotoManager() {
        photos = new LinkedList<>();
        index = new BST<>();
    }

    public void addPhoto(Photo p) {
        photos.insert(p);
        LinkedList<String>.Node tagNode = p.getTags().iterator();
        while (tagNode != null) {
            String tag = tagNode.data;
            LinkedList<Photo> list = index.search(tag);
            if (list == null) {
                list = new LinkedList<>();
                list.insert(p);
                index.insert(tag, list);
            } else {
                list.insert(p);
            }
            tagNode = tagNode.next;
        }
    }

    public void deletePhoto(String path) {
        LinkedList<Photo>.Node current = photos.iterator();
        Photo target = null;
        while (current != null) {
            if (current.data.getPath().equals(path)) {
                target = current.data;
                break;
            }
            current = current.next;
        }
        if (target == null) return;

        photos.remove(target);
        LinkedList<String>.Node tagNode = target.getTags().iterator();
        while (tagNode != null) {
            String tag = tagNode.data;
            LinkedList<Photo> list = index.search(tag);
            if (list != null) {
                list.remove(target);
                if (list.iterator() == null) {
                    index.delete(tag);
                }
            }
            tagNode = tagNode.next;
        }
    }

    public BST<LinkedList<Photo>> getPhotos() {
        return index;
    }

    public LinkedList<Photo> getAllPhotos() {
        return photos;
    }

    public LinkedList<Photo> searchByCondition(String condition) {
        String[] tags = condition.equals("") ? new String[0] : condition.split("\\s*AND\\s*");
        LinkedList<Photo> result = null;

        for (String tag : tags) {
            LinkedList<Photo> tagList = index.search(tag);
            if (tagList == null) return new LinkedList<>();

            if (result == null) {
                result = cloneList(tagList);
            } else {
                result = intersect(result, tagList);
            }
        }

        return result != null ? result : new LinkedList<>();
    }

    private LinkedList<Photo> cloneList(LinkedList<Photo> original) {
        LinkedList<Photo> clone = new LinkedList<>();
        LinkedList<Photo>.Node node = original.iterator();
        while (node != null) {
            clone.insert(node.data);
            node = node.next;
        }
        return clone;
    }

    private LinkedList<Photo> intersect(LinkedList<Photo> a, LinkedList<Photo> b) {
        LinkedList<Photo> result = new LinkedList<>();
        LinkedList<Photo>.Node node = a.iterator();
        while (node != null) {
            if (b.contains(node.data)) {
                result.insert(node.data);
            }
            node = node.next;
        }
        return result;
    }

    public void runInteractiveConsole() {
       Scanner scanner = new Scanner(System.in);
        System.out.println("\uD83D\uDCF8 Welcome to the Photo Manager (with Inverted Index)!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add a photo");
            System.out.println("2. Search photos by condition");
            System.out.println("3. Delete a photo");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter photo path: ");
                    String path = scanner.nextLine();
                    System.out.print("Enter tags separated by commas: ");
                    String tags = scanner.nextLine();
                    addPhoto(new Photo(path, toTagsLinkedList(tags)));
                    System.out.println("✅ Photo added.");
                    break;
                case "2":
                    System.out.print("Enter tag condition (e.g. 'animal AND snow'): ");
                    String condition = scanner.nextLine();
                    LinkedList<Photo> photos = searchByCondition(condition);
                    if (photos.iterator() == null) {
                        System.out.println("📭 No photos match the condition.");
                    } else {
                        System.out.println("🔍 Photos:");
                        LinkedList<Photo>.Node n = photos.iterator();
                        while (n != null) {
                            System.out.println("📷 " + n.data.getPath());
                            n = n.next;
                        }
                    }
                    break;
                case "3":
                    System.out.print("Enter photo path to delete: ");
                    String delPath = scanner.nextLine();
                    deletePhoto(delPath);
                    System.out.println("🗑️ Deleted if found.");
                    break;
                case "4":
                    System.out.println("👋 Goodbye!");
                    return;
                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }

    private LinkedList<String> toTagsLinkedList(String tags) {
        LinkedList<String> result = new LinkedList<>();
        String[] tagArray = tags.split("\\s*,\\s*");
        for (String tag : tagArray) {
            result.insert(tag);
        }
        return result;
    }
}