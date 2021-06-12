package edu.birzeit.maze;

import java.util.ArrayList;

public class Maze {
    private static String path = "";
    private static ArrayList<String> paths = new ArrayList<>();
    private static ArrayList<String> allPaths = new ArrayList<>();

    private static boolean isValidMove(int x, int y,
                                       boolean visited[][], int[][] mazeArr) {
        if (x == -1 || x == mazeArr.length || y == -1 ||
                y == mazeArr.length || visited[x][y] ||
                mazeArr[x][y] == 0)
            return false;

        return true;
    }

    private static void findSolutions(int x, int y, boolean[][] visited, int[][] mazeArr) {
        if (x == -1 || x == mazeArr.length || y == -1 ||
                y == mazeArr.length || visited[x][y] ||
                mazeArr[x][y] == 0)
            return;
        if (x == mazeArr.length - 1 && y == mazeArr.length - 1) {
            paths.add(path);
            return;
        }
        visited[x][y] = true;

        if (isValidMove(x + 1, y, visited, mazeArr)) {
            path += 'S';
            findSolutions(x + 1, y,
                    visited, mazeArr);
            path = path.substring(0, path.length() - 1);
        }

        if (isValidMove(x, y - 1, visited, mazeArr)) {
            path += 'W';
            findSolutions(x, y - 1,
                    visited, mazeArr);
            path = path.substring(0, path.length() - 1);
        }

        if (isValidMove(x, y + 1, visited, mazeArr)) {
            path += 'E';
            findSolutions(x, y + 1,
                    visited, mazeArr);
            path = path.substring(0, path.length() - 1);
        }

        if (isValidMove(x - 1, y, visited, mazeArr)) {
            path += 'N';
            findSolutions(x - 1, y,
                    visited, mazeArr);
            path = path.substring(0, path.length() - 1);
        }

        visited[x][y] = false;
    }

    private static String getPathIndexes(String path, int x, int y) {
        String result = "";
        path = path.trim();
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == 'S') {
                result += "(" + (x + 1) + "," + y + ")->S";
                x = x + 1;

            } else if (path.charAt(i) == 'W') {
                result += "(" + x + "," + (y - 1) + ")->W";
                y = y - 1;


            } else if (path.charAt(i) == 'E') {
                result += "(" + x + "," + (y + 1) + ")->E";
                y = y + 1;

            } else {
                result += "(" + (x - 1) + "," + y + ")->UP";
                x = x - 1;
            }
        }
        return result;
    }


    private static ArrayList<String> getPaths(int x, int y, int[][] mazeArr) {
        boolean[][] v = new boolean[mazeArr.length][mazeArr.length];
        findSolutions(x, y, v, mazeArr);
        return paths;
    }

    public static ArrayList<String> collectionPathsIndexes(int x, int y, int[][] mazeArr) {
        allPaths.clear();
        paths.clear();
        paths = getPaths(x, y, mazeArr);
        for (int i = 0; i < paths.size(); i++) {
            allPaths.add(getPathIndexes(paths.get(i), x, y));
        }

        return allPaths;
    }


    public static String getBestSolution(int x, int y, int[][] mazeArr) {
        String bestSol = "";

        ArrayList<String> solutions = collectionPathsIndexes(x, y, mazeArr);
        if (solutions.size() != 0) {
            bestSol = solutions.get(0);
            for (int i = 0; i < solutions.size(); i++) {
                if (solutions.get(i).length() < bestSol.length())
                    bestSol = solutions.get(i);
            }
        }
        return bestSol;
    }


}





