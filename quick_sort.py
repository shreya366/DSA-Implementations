def quick_sort(arr):
    if len(arr) <= 1:
        return arr
    else:
        pivot = arr[len(arr) // 2]  # Choose middle element as pivot
        left = [x for x in arr if x < pivot]
        middle = [x for x in arr if x == pivot]
        right = [x for x in arr if x > pivot]
        return quick_sort(left) + middle + quick_sort(right)

if __name__ == "__main__":
    sample_array = [33, 10, 59, 26, 41, 58, 18, 72, 4]
    print("Original Array:", sample_array)
    sorted_array = quick_sort(sample_array)
    print("Sorted Array:", sorted_array)
